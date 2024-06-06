package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.domain.Article;
import org.springframework.data.jpa.domain.Specification;

import java.beans.Transient;
import java.util.Objects;

public class ArticleSpecification {

    private ArticleSpecification(){

    }

    @Transient
    private static Specification<Article> withName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(name).concat("%"));
    }

    @Transient
    private static Specification<Article> withCategory(String category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category"), "%".concat(category).concat("%"));
    }

    @Transient
    private static Specification<Article> withPrice(Integer price) {
        return (root, query, criteriaBuilder) -> {return criteriaBuilder.equal(root.get("id"), price);
        };
    }

    @Transient
    private static Specification<Article> withCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%".concat(code).concat("%"));
    }

    @Transient
    private static Specification<Article> withStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status") ,status);
    }
    @Transient
    private static Specification<Article> orderByDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }
    @Transient
    private static Specification<Article> orderByAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }

    public static Specification<Article> getSpecification(ArticleCriteria criteria) {
        Specification<Article> specification = (root, query, criteriaBuilder) -> null;


        if (criteria.getName() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(ArticleSpecification.withName(criteria.getName()));
        }
        if (criteria.getCategory() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(ArticleSpecification.withCategory(criteria.getCategory()));
        }
        if (criteria.getPrice() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(ArticleSpecification.withPrice(criteria.getPrice()));
        }
        if (criteria.getCode() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(ArticleSpecification.withCode(criteria.getCode()));
        }
        if (criteria.getStatus() != null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(ArticleSpecification.withStatus(criteria.getStatus()));
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
