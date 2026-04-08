package com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record EstablishmentResponseDTO(

        Long id,

        String name,

        String description,

        String address,

        String phone,

        LocalDateTime createdAt
) {
}
