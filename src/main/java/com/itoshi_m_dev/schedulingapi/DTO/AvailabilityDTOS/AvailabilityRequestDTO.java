package com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityRequestDTO(

        @NotNull(message = "Profissional é obrigatório")
        @Positive(message = "ID do profissional deve ser positivo")
        Long professionalId,

        @NotNull(message = "Dia da semana é obrigatório")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Horário de início é obrigatório")
        LocalTime startTime,

        @NotNull(message = "Horário de fim é obrigatório")
        LocalTime endTime
) {
}
