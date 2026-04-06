package com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;

import java.math.BigDecimal;

public record ServiceResponseDTO(
        Long id,

        String name,

        String description,

        BigDecimal price,

        Integer durationMinutes,

        EstablishmentResponseDTO establishmentResponseDTO,

        ProfessionalResponseDTO professionalResponseDTO,

        Boolean isActive,

        AppointmentResponseDTO appointmentResponseDTO
) {
}
