package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceModelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "establishment", ignore = true)
    ServiceModel toEntity(ServiceModelRequestDTO dto);

    @Mapping(source = "establishment.id", target = "establishmentId")
    @Mapping(source = "professional.id", target = "professionalId")
    ServiceModelResponseDTO toDTO(ServiceModel entity);
}
