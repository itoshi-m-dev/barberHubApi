package com.itoshi_m_dev.schedulingapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Professional")
@Data
@RequiredArgsConstructor
public class Professional {

    private Long id;

    private String name;

    private String bio;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Establishment establishment;

    private List<Service> serviceList;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<Availability> availabilityList;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;


}
