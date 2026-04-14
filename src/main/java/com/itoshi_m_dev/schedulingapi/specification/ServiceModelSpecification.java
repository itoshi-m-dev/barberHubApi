package com.itoshi_m_dev.schedulingapi.specification;

import com.itoshi_m_dev.schedulingapi.model.ServiceModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ServiceModelEspecification {

    public static Specification<ServiceModel> nameContains(String name){
        return (root, query, criteriaBuilder) -> {
            if(name == null || name.isBlank()){
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), name.toLowerCase());
        };

    }

    public static Specification<ServiceModel> priceBetween(BigDecimal min, BigDecimal max){
        return (root, query, criteriaBuilder) -> {
            if(min == null || max == null){
                return null;
            }

            return criteriaBuilder.between(root.get("price"), min, max);
        };
        
    }
}
