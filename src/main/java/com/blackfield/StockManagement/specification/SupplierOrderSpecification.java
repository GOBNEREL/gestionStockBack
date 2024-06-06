package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.SupplierOrderCriteria;
import com.blackfield.StockManagement.domain.SupplierOrder;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.Objects;

public class SupplierOrderSpecification {

    private SupplierOrderSpecification(){

    }

    @Transient
    private static Specification<SupplierOrder> withSupplierName(String supplierName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("supplierName"), "%".concat(supplierName).concat("%"));
    }

    @Transient
    private static Specification<SupplierOrder> withArticle(String article) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("article"), "%".concat(article).concat("%"));
    }

    @Transient
    private static Specification<SupplierOrder> withTotalPrice(Integer totalPrice) {
        return (root, query, criteriaBuilder) -> {return criteriaBuilder.equal(root.get("totalPrice"), totalPrice);
        };
    }

    @Transient
    private static Specification<SupplierOrder> withCommandDate(LocalDate commandDate) {
        LocalDate firstDayOfMonth = commandDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = commandDate.withDayOfMonth(commandDate.lengthOfMonth());

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("commandDate"), firstDayOfMonth, lastDayOfMonth);
    }

    @Transient
    private static Specification<SupplierOrder> withCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%".concat(code).concat("%"));
    }

    @Transient
    private static Specification<SupplierOrder> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<SupplierOrder> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<SupplierOrder> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<SupplierOrder> getSpecification(SupplierOrderCriteria criteria) {
        Specification<SupplierOrder> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getSupplierName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierOrderSpecification.withSupplierName(criteria.getSupplierName()));
        }
        if (criteria.getArticle() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierOrderSpecification.withArticle(criteria.getArticle()));
        }
        if (criteria.getTotalPrice() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierOrderSpecification.withTotalPrice(criteria.getTotalPrice()));
        }
        if (criteria.getCommandDate() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withCommandDate(criteria.getCommandDate()));
        }
        if (criteria.getCode() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierOrderSpecification.withCode(criteria.getCode()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(SupplierOrderSpecification.withStatus(criteria.getStatus()));
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