package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.exception.ServiceAlreadyExistsException;
import com.itoshi_m_dev.schedulingapi.mapper.ServiceModelMapper;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import com.itoshi_m_dev.schedulingapi.repositories.EstablishmentRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ProfessionalRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ServiceRepository;
import com.itoshi_m_dev.schedulingapi.specification.ServiceModelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.itoshi_m_dev.schedulingapi.validators.Validator.notNull;

@Service
@RequiredArgsConstructor
public class ServiceModelService {

    private final ServiceRepository repository;
    private final ProfessionalRepository professionalRepository;
    private final EstablishmentRepository establishmentRepository;
    private final ServiceModelMapper mapper;

    public ServiceModelResponseDTO createServiceInEstablishmentById(
            Long establishmentId,
            ServiceModelRequestDTO dto) {

        notNull(establishmentId);
        notNull(dto);

        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum estabelecimento encontrado com ID: " + establishmentId
                ));

        if (repository.existsByNameAndEstablishmentId(dto.name(), establishmentId)) {
            throw new ServiceAlreadyExistsException("Serviço já criado.");
        }

        ServiceModel entity = mapper.toEntity(dto);
        entity.setEstablishment(establishment);
        establishment.getServices().add(entity);

        repository.save(entity);

        return mapper.toDTO(entity);
    }


    public List<ServiceModelResponseDTO> listAllServicesInEstablishment(Long establishmentId){
        notNull(establishmentId);

        if(!establishmentRepository.existsById(establishmentId)){
            throw new ResourceNotFoundException(
                    "Nenhum estabelecimento encontrado com ID: " + establishmentId
            );
        }

        List<ServiceModel> services = repository.findByEstablishmentId(establishmentId);

        return services.stream().map(mapper::toDTO).toList();
    }

    public ServiceModelResponseDTO findById(Long id){
        notNull(id);

        ServiceModel serviceModel = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Nenhum serviço encontrado com esse ID : " + id));

        return mapper.toDTO(serviceModel);
    }

    public ServiceModelResponseDTO updateServiceInEstablishment(Long establishmentId,
                                                                Long serviceId,
                                                                ServiceModelRequestDTO dto){
        notNull(establishmentId);
        notNull(serviceId);
        notNull(dto);

        ServiceModel serviceModel = repository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum serviço encontrado com o ID: " + serviceId
                ));

        if (!serviceModel.getEstablishment().getId().equals(establishmentId)) {
            throw new ResourceNotFoundException(
                    "O serviço não pertence ao estabelecimento informado"
            );
        }

        updateService(serviceModel, dto);

        repository.save(serviceModel);

        return mapper.toDTO(serviceModel);
    }


    public void deleteServiceByEstablishmentId(Long establishmentId, Long serviceId){
        notNull(establishmentId);
        notNull(serviceId);

        ServiceModel serviceModel =  repository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum serviço encontrado com o ID: " + serviceId
                ));

        if(!serviceModel.getEstablishment().getId().equals(establishmentId)){
            throw new ResourceNotFoundException(
                    "O serviço não pertence ao estabelecimento informado"
            );
        }

        repository.delete(serviceModel);
    }


    public Page<ServiceModelResponseDTO> findAll(String name,
                                                 BigDecimal min, BigDecimal max,
                                                 Integer minutes,
                                                 Pageable pageable){

        Specification<ServiceModel> spec = Specification.where(null);

        if (name != null){
            spec = spec.and(ServiceModelSpecification.nameContains(name));
        }

        if(min != null && max != null){
            spec = spec.and(ServiceModelSpecification.priceBetween(min,max));
        }

        if(minutes != null){
            spec = spec.and(ServiceModelSpecification.durationMinutesLessThan(minutes));
        }

        return repository.findAll(spec, pageable).map(mapper::toDTO);


    }

    public ServiceModelResponseDTO addProfessionalToService(Long establishmentId, Long serviceId, Long professionalId){

        ServiceModel entity = repository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum serviço foi encontrado com este ID: " + serviceId));

        if(!entity.getEstablishment().getId().equals(establishmentId)){
            throw new ResourceNotFoundException("O serviço nao pertence ao estabelecimento informado");
        }

        if(entity.getProfessional() != null){
            throw new ServiceAlreadyExistsException("Ja existe um profissional vinculado a este serviço");
        }

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional foi encontrado com ID: " + professionalId));

        if(!professional.getEstablishment().getId().equals(establishmentId)){
            throw new ResourceNotFoundException("O profissional nao pertence ao estabelecimento informado");
        }

        entity.setProfessional(professional);
        professional.getServiceList().add(entity);

        repository.save(entity);

        return mapper.toDTO(entity);


    }



    private void updateService(ServiceModel serviceModel, ServiceModelRequestDTO dto) {

        if (dto.name() != null) {
            serviceModel.setName(dto.name());
        }

        if (dto.description() != null) {
            serviceModel.setDescription(dto.description());
        }

        if (dto.price() != null) {
            serviceModel.setPrice(dto.price());
        }

        if (dto.durationMinutes() != null) {
            serviceModel.setDurationMinutes(dto.durationMinutes());
        }

    }



}
