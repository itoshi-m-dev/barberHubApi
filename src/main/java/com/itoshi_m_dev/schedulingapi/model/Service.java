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
    @JoinColumn(name = "establishment")
    private Establishment establishment;

    private boolean isActive = true;

}
