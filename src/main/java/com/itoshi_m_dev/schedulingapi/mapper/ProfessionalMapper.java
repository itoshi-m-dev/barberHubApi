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


    @Mapping(source = "establishment.id", target = "establishmentId")
    @Mapping(source = "serviceList", target = "services")
    @Mapping(source = "availabilityList", target = "availabilities")
    @Mapping(source = "appointmentList", target = "appointments")
    ProfessionalResponseDTO toDTO(Professional entity);
}
