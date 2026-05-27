package com.itoshi_m_dev.schedulingapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    private String scope;


}
