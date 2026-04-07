package com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceRequestDTO;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record EstablishmentRequestDTO(

        @NotBlank
        @Size(min = 4, max = 50, message = "Nome deve ter 4 caracteres ao menos.")
        String name,

        @NotBlank
        @Size(min = 20, max = 200, message = "Pelo menos uma descrição com 20 caracteres é necessário.")
        String description,

        @NotBlank
        @Size(min = 40, max = 100, message = "Endereço deve conter entre 40 e 100 caracteres.")
        String address,

        @NotBlank
        @Size(min = 11, max = 11, message = "Telefone deve ter 11 dígitos.")
        String phone,

        @NotNull
        @PastOrPresent(message = "Estabelecimento nao pode ser criado em uma data futura.")
        LocalDateTime createdAt

) {
}
