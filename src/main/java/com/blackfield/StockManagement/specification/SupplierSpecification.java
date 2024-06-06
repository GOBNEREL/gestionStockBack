package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.SupplierCriteria;
import com.blackfield.StockManagement.domain.Supplier;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.util.Objects;

public class SupplierSpecification {

    private SupplierSpecification(){

    }

    @Transient
    private static Specification<Supplier> withName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(name).concat("%"));
    }

    @Transient
    private static Specification<Supplier> withPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phoneNumber"), "%".concat(phoneNumber).concat("%"));
    }

    @Transient
    private static Specification<Supplier> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<Supplier> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<Supplier> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<Supplier> getSpecification(SupplierCriteria criteria) {
        Specification<Supplier> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierSpecification.withName(criteria.getName()));
        }
        if (criteria.getPhoneNumber() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierSpecification.withPhoneNumber(criteria.getPhoneNumber()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierSpecification.withStatus(criteria.getStatus()));
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
