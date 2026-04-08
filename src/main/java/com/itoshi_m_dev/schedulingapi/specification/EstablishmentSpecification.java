package com.itoshi_m_dev.schedulingapi.specification;

import com.itoshi_m_dev.schedulingapi.model.Establishment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EstablishmentSpecification {

    public static Specification<Establishment> nameContains(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Establishment> createdBefore(LocalDateTime dateTime) {
        return ((root, query, criteriaBuilder) -> {
            if (dateTime == null) {
                return null;
            }
            return criteriaBuilder.lessThan(root.get("createdAt"), dateTime);
        });

    }
}
