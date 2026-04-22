package com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentResponseDTO(
        Long id,

        Long professionalId,

        Long serviceId,

        LocalDateTime scheduledAt,

        Integer durationMinutes,

        BigDecimal price,

        AppointmentStatus status,

        String notes,

        LocalDateTime createdAt



) {
}
