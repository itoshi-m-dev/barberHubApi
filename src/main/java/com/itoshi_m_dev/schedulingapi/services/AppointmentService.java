package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;
import com.itoshi_m_dev.schedulingapi.exception.CancelledApointmentException;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.mapper.AppointmentMapper;
import com.itoshi_m_dev.schedulingapi.model.Appointment;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import com.itoshi_m_dev.schedulingapi.repositories.AppointmentRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ProfessionalRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper mapper;

    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Professional professional = professionalRepository.findById(dto.professionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: "
                        + dto.professionalId()));

        ServiceModel service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum serviço encontrado com este ID: "
                        + dto.serviceId()));

        if (!professional.getServiceList().contains(service)) {
            throw new IllegalArgumentException("O funcionario nao faz o serviço desejado. ");
        }

        Appointment entity = mapper.toEntity(dto);
        entity.setProfessional(professional);
        entity.setService(service);

        professional.getAppointmentList().add(entity);
        service.setAppointment(entity);

        repository.save(entity);

        return mapper.toDTO(entity);

    }

    public AppointmentResponseDTO detailAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum agendamento encontrado com este ID" + id));

        return mapper.toDTO(appointment);

    }

    public void cancelAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum agendamento encontrado com este ID" + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new CancelledApointmentException("Este agendamento ja esta cancelado");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        repository.save(appointment);
    }

    public void confirmAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum agendamento encontrado com este ID" + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new CancelledApointmentException("Nao e possivel confirmar um agendamento ja cancelado.");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);

        repository.save(appointment);

    }

    public void completeAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum agendamento encontrado com este ID" + id));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new CancelledApointmentException("Nao e possivel completar um agendamento ja cancelado.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        repository.save(appointment);

    }

    public List<AppointmentResponseDTO> getAppointmentsByProfessionalAndDate(Long professionalId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Appointment> appointments = repository.findByProfessionalIdAndCreatedAtBetween(
                professionalId, startOfDay, endOfDay
        );

        return appointments.stream().map(mapper::toDTO).toList();
    }

    public List<AppointmentResponseDTO> findAll(AppointmentStatus status) {
        List<Appointment> appointments = Optional.ofNullable(status)
                .map(repository::findByStatus)
                .orElseGet(repository::findAll);

        return appointments.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<AppointmentResponseDTO> getByProfessionalId(Long id) {
        List<Appointment> appointments = repository.findByProfessionalId(id);

        return appointments.stream().map(mapper::toDTO).toList();   
    }


}
