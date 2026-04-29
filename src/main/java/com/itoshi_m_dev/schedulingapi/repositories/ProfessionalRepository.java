package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Professional findByName(String name);

    Page<Professional> findByEstablishmentId(Long establishmentId, Pageable pageable);


    Optional<Object> findByIdAndEstablishmentId(Long professionalId, Long establishmentId);
}
