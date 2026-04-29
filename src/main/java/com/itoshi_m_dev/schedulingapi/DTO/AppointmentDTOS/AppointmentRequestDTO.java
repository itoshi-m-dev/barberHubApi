package com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull(message = "Profissional é obrigatório")
        @Positive(message = "ID do profissional deve ser positivo")
        Long professionalId,

        @NotNull(message = "Serviço é obrigatório")
        @Positive(message = "ID do serviço deve ser positivo")
        Long serviceId,

        @NotNull(message = "Data e horário são obrigatórios")
        @FutureOrPresent(message = "Agendamento deve ser no presente ou futuro")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime scheduledAt,

        @NotNull(message = "Status é obrigatório")
        AppointmentStatus status,

        @Size(max = 500, message = "Notas devem ter no máximo 500 caracteres")
        String notes,

        @PastOrPresent(message = "A data de criação deve ser presente ou passada")
        @NotNull(message = "Data e horario de criação sao obrigatorias")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt


) {
}
