package com.itoshi_m_dev.schedulingapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "Availability")
@RequiredArgsConstructor
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;



}
