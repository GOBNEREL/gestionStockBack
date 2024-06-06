package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.domain.Stock;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.util.Objects;

public class StockSpecification {

    private StockSpecification(){

    }

    @Transient
    private static Specification<Stock> withName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(name).concat("%"));
    }

    @Transient
    private static Specification<Stock> withCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%".concat(code).concat("%"));
    }

    @Transient
    private static Specification<Stock> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<Stock> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<Stock> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<Stock> getSpecification(StockCriteria criteria) {
        Specification<Stock> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(StockSpecification.withName(criteria.getName()));
        }
        if (criteria.getCode() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(StockSpecification.withCode(criteria.getCode()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(StockSpecification.withStatus(criteria.getStatus()));
        }
        if (criteria.getClassement() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(orderByDesc());
        }
        if (criteria.getClassement() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(orderByAsc());
        }

        return specification;
    }
}

