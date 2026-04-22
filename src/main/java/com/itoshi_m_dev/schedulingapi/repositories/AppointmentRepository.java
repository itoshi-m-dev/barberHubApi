package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;
import com.itoshi_m_dev.schedulingapi.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByProfessionalId(Long professionalId);

    List<Appointment> findByProfessionalIdAndCreatedAtBetween(
            Long professionalId,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
}
