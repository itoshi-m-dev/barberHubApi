package com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS;

import jakarta.validation.constraints.*;


import java.math.BigDecimal;


public record ServiceModelRequestDTO(

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String name,

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    String description,

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    BigDecimal price,

    @NotNull(message = "Duração é obrigatória")
    @Min(value = 1, message = "Duração deve ser de no mínimo 1 minuto")
    Integer durationMinutes,

    @NotNull(message = "Estabelecimento é obrigatório")
    Long establishmentId,

    Long professionalId,

    Boolean isActive,

    Long appointmentId



) {
}
