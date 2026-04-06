package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
}
