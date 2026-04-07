package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceResponseDTO;
import com.itoshi_m_dev.schedulingapi.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProfessionalMapper.class, AppointmentMapper.class})
public interface ServiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "establishment", ignore = true)
    @Mapping(target = "professional", ignore = true)
    @Mapping(target= "appointment", ignore = true)
    Service toEntity(ServiceRequestDTO dto);

    @Mapping(source = "establishment", target = "establishmentResponseDTO")
    @Mapping(source = "professional", target = "professionalResponseDTO")
    @Mapping(source = "appointment", target = "appointmentResponseDTO")
    ServiceResponseDTO toDTO(Service entity);
}
