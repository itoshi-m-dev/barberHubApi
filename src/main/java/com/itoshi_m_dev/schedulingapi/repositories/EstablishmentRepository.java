package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    Establishment findByName(String name);

    List<Establishment> FindByCreatedAtBefore(LocalDateTime dateTime);
}
