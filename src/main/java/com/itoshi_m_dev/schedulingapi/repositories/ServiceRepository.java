package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findByName(String name);

    List<Service> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Service> findByDurationMinutesLessThan(Integer minutes);

    List<Service> findByEstablishmentId(Long establishmentId);

    List<Service> findByProfessionalId(Long professionalId);
    
}
