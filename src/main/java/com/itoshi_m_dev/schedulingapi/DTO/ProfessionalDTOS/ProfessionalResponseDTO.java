package com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfessionalResponseDTO(
        Long id,
        String name,
        String bio,

        Long establishmentId,

        List<ServiceModelResponseDTO> services,

        List<AvailabilityResponseDTO> availabilities,

        List<AppointmentResponseDTO> appointments
) {
}
