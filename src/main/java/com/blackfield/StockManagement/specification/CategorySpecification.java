package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.domain.Category;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.util.Objects;

public class CategorySpecification {

    private CategorySpecification(){

    }

    @Transient
    private static Specification<Category> withNom(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(name).concat("%"));
    }

    @Transient
    private static Specification<Category> withCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%".concat(code).concat("%"));
    }

    @Transient
    private static Specification<Category> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<Category> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<Category> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<Category> getSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CategorySpecification.withNom(criteria.getName()));
        }
        if (criteria.getCode() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CategorySpecification.withCode(criteria.getCode()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(CategorySpecification.withStatus(criteria.getStatus()));
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