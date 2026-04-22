package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professional", ignore = true)
    @Mapping(target = "service", ignore = true)
    Appointment toEntity(AppointmentRequestDTO dto);

    @Mapping(source = "professional.id", target = "professionalId")
    @Mapping(source = "service.id", target = "serviceId")
    AppointmentResponseDTO toDTO(Appointment entity);


}
