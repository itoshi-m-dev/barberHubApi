package com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceResponseDTO;

import java.util.List;

public record ProfessionalResponseDTO(
        Long id,

        String name,

        String bio,

        EstablishmentResponseDTO establishmentResponseDTO,

        List<ServiceResponseDTO> serviceResponseDTO,

        List<AvailabilityResponseDTO> availabilityResponseDTO,

        List<AppointmentResponseDTO> appointmentResponseDTO
) {
}
