package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.Appointment;
import com.itoshi_m_dev.schedulingapi.model.Availability;
import com.itoshi_m_dev.schedulingapi.model.Establishment;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "establishment", ignore = true)
    Professional toEntity(ProfessionalRequestDTO dto);


    @Mapping(source = "establishment", target = "establishmentResponseDTO")
    @Mapping(source = "serviceList", target = "serviceResponseDTO")
    @Mapping(source = "availabilityList", target = "availabilityResponseDTO")
    @Mapping(source = "appointmentList", target = "appointmentResponseDTO")
    ProfessionalResponseDTO toDTO(Professional entity);
}
