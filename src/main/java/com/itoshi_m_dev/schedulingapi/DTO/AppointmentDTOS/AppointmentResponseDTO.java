package com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,

        ProfessionalResponseDTO professionalResponseDTO,

        ServiceModelResponseDTO serviceResponseDTO,

        LocalDateTime scheduledAt,

        Integer durationMinutes,

        BigDecimal price,

        AppointmentStatus status,

        String notes,

        LocalDateTime createdAt



) {
}
