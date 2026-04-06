package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Availability;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import com.itoshi_m_dev.schedulingapi.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Professional findByName(String name);

    List<Professional> findByEstablishmentId(Long establishmentId);

    List<Service> findByProfessionalId(Long professionalId);

}
