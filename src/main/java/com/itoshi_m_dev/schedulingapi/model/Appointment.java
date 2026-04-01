package com.itoshi_m_dev.schedulingapi.model;

import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
@RequiredArgsConstructor
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private Service service;

    private LocalDateTime scheduledAt;

    private Integer durationMinutes;

    private BigDecimal price;

    private AppointmentStatus status;

    private String notes;

    private LocalDateTime createdAt;

}
