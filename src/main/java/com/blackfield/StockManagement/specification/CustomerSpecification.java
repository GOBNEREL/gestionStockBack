package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.CustomerCriteria;
import com.blackfield.StockManagement.domain.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.util.Objects;

public class CustomerSpecification {

    private CustomerSpecification(){

    }

    @Transient
    private static Specification<Customer> withName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(name).concat("%"));
    }

    @Transient
    private static Specification<Customer> withPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phoneNumber"), "%".concat(phoneNumber).concat("%"));
    }

    @Transient
    private static Specification<Customer> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<Customer> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<Customer> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<Customer> getSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerSpecification.withName(criteria.getName()));
        }
        if (criteria.getPhoneNumber() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerSpecification.withPhoneNumber(criteria.getPhoneNumber()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerSpecification.withStatus(criteria.getStatus()));
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
