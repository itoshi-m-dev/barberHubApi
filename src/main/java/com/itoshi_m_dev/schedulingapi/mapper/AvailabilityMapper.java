package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professional", ignore = true)
    Availability toEntity(AvailabilityRequestDTO dto);

    @Mapping(source = "professional.id", target = "professionalId")
    AvailabilityResponseDTO toDTO(Availability entity);
}
