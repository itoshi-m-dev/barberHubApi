package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientId(String clientId);

}
