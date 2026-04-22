package com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceModelResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer durationMinutes,
        Long establishmentId,
        Long professionalId
) {
}
