package com.itoshi_m_dev.schedulingapi.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstablishmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professionals", ignore = true)
    @Mapping(target = "services", ignore = true)
    Establishment toEntity(EstablishmentRequestDTO dto);

    @Mapping(target = "professionals", source = "professionals")
    EstablishmentResponseDTO toDTO(Establishment entity);
}
