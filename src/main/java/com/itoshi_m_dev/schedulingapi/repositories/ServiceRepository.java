package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceModel, Long>, JpaSpecificationExecutor<ServiceModel> {

    List<ServiceModel> findByEstablishmentId(Long establishmentId);

    List<ServiceModel> findByProfessionalId(Long professionalId);

    boolean existsByNameAndEstablishmentId(String name, Long establishmentId);


}
