package com.itoshi_m_dev.schedulingapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Service")
@Data
@RequiredArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private boolean isActive = true;

    @OneToOne(mappedBy = "service")
    private Appointment appointment;

}
