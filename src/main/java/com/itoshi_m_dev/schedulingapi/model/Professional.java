package com.itoshi_m_dev.schedulingapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Professional")
@Data
@RequiredArgsConstructor
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String bio;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Establishment establishment;

    @OneToMany(mappedBy = "professional")
    private List<Service> serviceList = new ArrayList<>();

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<Availability> availabilityList = new ArrayList<>();

    @OneToMany(mappedBy = "professional")
    private List<Appointment> appointmentList = new ArrayList<>();



}
