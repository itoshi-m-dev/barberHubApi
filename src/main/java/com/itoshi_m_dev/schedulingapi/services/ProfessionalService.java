package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.exception.ServiceAlreadyExistsException;
import com.itoshi_m_dev.schedulingapi.mapper.ProfessionalMapper;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import com.itoshi_m_dev.schedulingapi.repositories.EstablishmentRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ProfessionalRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository repository;
    private final ProfessionalMapper mapper;
    private final EstablishmentRepository establishmentRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public ProfessionalResponseDTO addProfessionalToEstablishment(Long establishmentId, ProfessionalRequestDTO dto) {

        if (!dto.establishmentId().equals(establishmentId)) {
            throw new ResourceNotFoundException("IDs incompatíveis");
        }

        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado"));

        Professional entity = mapper.toEntity(dto);

        boolean exists = establishment.getProfessionals()
                .stream()
                .anyMatch(p -> p.getName().equals(dto.name()));

        if (exists) {
            throw new IllegalArgumentException("Profissional já existe no estabelecimento");
        }

        entity.setEstablishment(establishment);
        establishment.getProfessionals().add(entity);

        repository.save(entity);

        return mapper.toDTO(entity);
    }

    @Transactional
    public Page<ProfessionalResponseDTO> findAllProfessionalByEstablishmentId(Long establishmentId,
                                                                              Pageable pageable) {
        if (!establishmentRepository.existsById(establishmentId)) {
            throw new ResourceNotFoundException("Nenhum estabelecimento encontrado com este ID: " + establishmentId);
        }

        Page<Professional> professionals = repository.findByEstablishmentId(establishmentId, pageable);

        return professionals.map(mapper::toDTO);

    }

    public ProfessionalResponseDTO professionalDetails(Long establishmentId, Long professionalId) {
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum estabelecimento encontrado com este ID: " + establishmentId));

        Professional professional = repository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId));

        if (!establishment.getProfessionals().contains(professional)) {
            throw new ResourceNotFoundException("O profissional não pertence ao estabelecimento informado. " +
                    "Pertence ao estabelecimento de ID: " + professional.getEstablishment().getId());
        }

        return mapper.toDTO(professional);
    }

    public ProfessionalResponseDTO updateProfessional(Long establishmentId, Long professionalId,
                                                      ProfessionalRequestDTO dto) {

        Professional professional = repository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId));

        if (!professional.getEstablishment().getId().equals(establishmentId)) {
            throw new ResourceNotFoundException(
                    "O profissional não pertence ao estabelecimento informado. " +
                            "Pertence ao estabelecimento de ID: " + professional.getEstablishment().getId()
            );
        }

        Professional saved = update(professional, dto);

        repository.save(saved);

        return mapper.toDTO(saved);

    }

    public void deleteProfessional(Long establishmentId, Long professionalId){

        Professional professional = repository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId));

        if(!professional.getEstablishment().getId().equals(establishmentId)){
            throw new ResourceNotFoundException(
                    "O profissional não pertence ao estabelecimento informado. " +
                            "Pertence ao estabelecimento de ID: " + professional.getEstablishment().getId()
            );
        }

        repository.delete(professional);

    }

    public void addServiceToProfessional(Long professionalId, Long serviceId){
        Professional professional = repository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId));

        ServiceModel service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum serviço encontrado com este ID: " + serviceId));

        if(!professional.getEstablishment().getId().equals(service.getEstablishment().getId())){
            throw new ResourceNotFoundException("O profissional e o serviço designado são de estabelecimentos diferentes");
        }

        if(professional.getServiceList().contains(service)){
            throw new ServiceAlreadyExistsException("O profissional ja tem o serviço em questão.");
        }

        professional.getServiceList().add(service);
        service.setProfessional(professional);

        repository.save(professional);


    }

    public Professional update(Professional professional, ProfessionalRequestDTO dto) {

        if (dto.name() != null) {
            professional.setName(dto.name());
        }

        if (dto.bio() != null) {
            professional.setBio(dto.bio());
        }

        if (dto.establishmentId() != null &&
                !dto.establishmentId().equals(professional.getEstablishment().getId())) {

            Establishment establishment = establishmentRepository.findById(dto.establishmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Nenhum estabelecimento encontrado com este ID: " + dto.establishmentId()
                    ));

            professional.setEstablishment(establishment);
        }

        return professional;
    }

}
