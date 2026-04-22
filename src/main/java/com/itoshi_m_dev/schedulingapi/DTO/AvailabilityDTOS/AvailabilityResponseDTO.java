package com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.DayOfWeek;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AvailabilityResponseDTO(

        Long id,

        Long professionalId,

        DayOfWeek dayOfWeek,

        LocalTime startTime,

        LocalTime endTime
) {
}
