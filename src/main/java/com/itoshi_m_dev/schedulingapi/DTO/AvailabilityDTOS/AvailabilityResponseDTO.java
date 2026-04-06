package com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityResponseDTO(

        Long id,

        ProfessionalResponseDTO professionalResponseDTO,

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime
) {
}
