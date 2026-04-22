package com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstablishmentResponseDTO(

        Long id,

        String name,

        String description,

        String address,

        String phone,

        LocalDateTime createdAt,

        List<ProfessionalResponseDTO> professionals
) {
}
