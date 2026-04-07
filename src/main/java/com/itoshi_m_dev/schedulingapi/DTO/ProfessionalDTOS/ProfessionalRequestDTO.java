package com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProfessionalRequestDTO(

        @NotBlank(message = "Profissional deve ter um nome.")
        @Size(max = 100, message = "Nome deve conter no máximo 100 caracteres")
        String name,

        @Size(max = 255, message = "Bio deve ter no máximo 255 caracteres.")
        String bio,

        @NotNull(message = "Profissional deve ter um estabelecimento")
        Long establishmentId

) {
}
