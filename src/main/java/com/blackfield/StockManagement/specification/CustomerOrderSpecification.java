package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.CustomerOrderCriteria;
import com.blackfield.StockManagement.domain.CustomerOrder;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerOrderSpecification {

    private CustomerOrderSpecification(){

    }

    @Transient
    private static Specification<CustomerOrder> withCustomerName(String customerName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customerName"), "%".concat(customerName).concat("%"));
    }

    @Transient
    private static Specification<CustomerOrder> withArticle(String article) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("article"), "%".concat(article).concat("%"));
    }

    @Transient
    private static Specification<CustomerOrder> withTotalPrice(Integer totalPrice) {
        return (root, query, criteriaBuilder) -> {return criteriaBuilder.equal(root.get("totalPrice"), totalPrice);
        };
    }

    @Transient
    private static Specification<CustomerOrder> withCommandDate(LocalDate commandDate) {
        LocalDate firstDayOfMonth = commandDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = commandDate.withDayOfMonth(commandDate.lengthOfMonth());

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("commandDate"), firstDayOfMonth, lastDayOfMonth);
    }

    @Transient
    private static Specification<CustomerOrder> withCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%".concat(code).concat("%"));
    }

    @Transient
    private static Specification<CustomerOrder> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<CustomerOrder> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<CustomerOrder> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<CustomerOrder> getSpecification(CustomerOrderCriteria criteria) {
        Specification<CustomerOrder> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getCustomerName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerOrderSpecification.withCustomerName(criteria.getCustomerName()));
        }
        if (criteria.getArticle() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerOrderSpecification.withArticle(criteria.getArticle()));
        }
        if (criteria.getTotalPrice() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerOrderSpecification.withTotalPrice(criteria.getTotalPrice()));
        }
        if (criteria.getCommandDate() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withCommandDate(criteria.getCommandDate()));
        }
        if (criteria.getCode() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerOrderSpecification.withCode(criteria.getCode()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CustomerOrderSpecification.withStatus(criteria.getStatus()));
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