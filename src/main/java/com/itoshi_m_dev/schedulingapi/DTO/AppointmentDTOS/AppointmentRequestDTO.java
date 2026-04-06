package com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS;

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
        LocalDateTime scheduledAt,

        @NotNull(message = "Duração é obrigatória")
        @Min(value = 1, message = "Duração deve ser no mínimo 1 minuto")
        Integer durationMinutes,

        @NotNull(message = "Preço é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
        BigDecimal price,

        @NotNull(message = "Status é obrigatório")
        AppointmentStatus status,

        @Size(max = 500, message = "Notas devem ter no máximo 500 caracteres")
        String notes,

        @PastOrPresent(message = "A data de criação deve ser presente ou passada")
        @NotNull(message = "Data e horario de criação sao obrigatorias")
        LocalDateTime createdAt


) {
}
