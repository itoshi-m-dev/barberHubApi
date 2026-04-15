package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.mapper.EstablishmentMapper;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import com.itoshi_m_dev.schedulingapi.repositories.EstablishmentRepository;
import com.itoshi_m_dev.schedulingapi.specification.EstablishmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.itoshi_m_dev.schedulingapi.validators.Validator.notNull;

@Service
@RequiredArgsConstructor
public class EstablishmentService {

    private final EstablishmentRepository repository;
    private final EstablishmentMapper mapper;

    public EstablishmentResponseDTO create(EstablishmentRequestDTO dto){
        notNull(dto);

        Establishment establishment = mapper.toEntity(dto);

        Establishment saved = repository.save(establishment);

        return mapper.toDTO(establishment);
    }

    public Page<EstablishmentResponseDTO> findAll(String name, LocalDateTime createdAt, Pageable pageable) {

        Specification<Establishment> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(EstablishmentSpecification.nameContains(name));
        }

        if (createdAt != null) {
            spec = spec.and(EstablishmentSpecification.createdBefore(createdAt));
        }

        return repository.findAll(spec, pageable).map(mapper::toDTO);

    }

    public EstablishmentResponseDTO findById(Long id){
        notNull(id);

        Establishment establishment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum estabelecimento encontrado com ID: " + id));

        EstablishmentResponseDTO result = mapper.toDTO(establishment);

        return result;
    }

    public EstablishmentResponseDTO updateById(Long id, EstablishmentRequestDTO dto){
        notNull(id);
        notNull(dto);

        Establishment establishment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum estabelecimento encontrado com ID: " + id));

        Establishment updated = update(establishment, dto);

        Establishment saved = repository.save(updated);

        return mapper.toDTO(saved);

    }


    public void deleteById(Long id){
        notNull(id);

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Nenhum estabelecimento encontrado com o ID: " + id);
        }

        repository.deleteById(id);
    }


    private Establishment update(Establishment establishment, EstablishmentRequestDTO dto) {
        if (dto.name() != null) {
            establishment.setName(dto.name());
        }
        if (dto.description() != null) {
            establishment.setDescription(dto.description());
        }
        if (dto.address() != null) {
            establishment.setAddress(dto.address());
        }
        if (dto.phone() != null) {
            establishment.setPhone(dto.phone());
        }
        return establishment;

    }
}
