package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.mapper.ServiceModelMapper;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import com.itoshi_m_dev.schedulingapi.repositories.EstablishmentRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServiceRepository repository;
    private final EstablishmentRepository establishmentRepository;
    private final ServiceModelMapper mapper;

    public ServiceModelResponseDTO createServiceInEstablishmentById(Long id, ServiceModelRequestDTO dto){
        Establishment establishment = establishmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum estabelecimento encontrado com ID: " + id));

        List<ServiceModel> services = establishment.getServices();
        ServiceModel entity = mapper.toEntity(dto);
        entity.setEstablishment(establishment);
        services.add(entity);

        repository.save(entity);
        establishmentRepository.save(establishment);

        return mapper.toDTO(entity);
    }

}
