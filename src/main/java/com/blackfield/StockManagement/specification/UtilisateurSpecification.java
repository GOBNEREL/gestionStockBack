package com.blackfield.StockManagement.specification;

import com.blackfield.StockManagement.criteria.UtilisateurCriteria;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.google.common.base.Strings;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class UtilisateurSpecification {
    private static Specification<UsersDomain> withNom(String nom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%".concat(nom).concat("%"));
    }
    private static Specification<UsersDomain> withTel(String telephone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phone"), "%".concat(telephone).concat("%"));
    }
    private static Specification<UsersDomain> withEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%".concat(email).concat("%"));
    }

    private static Specification<UsersDomain> withLogin(String login) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("login"), "%".concat(login).concat("%"));
    }


    private static Specification<UsersDomain> withActif(Boolean actif) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("activated"), actif);
    }

    @Transient
    private static Specification<UsersDomain> orderByIdDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))).getRestriction();
    }

    @Transient
    private static Specification<UsersDomain> orderByIdAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
    }


    public static Specification<UsersDomain> getSpecification(UtilisateurCriteria criteria) {

        Specification<UsersDomain> specification = null;


        if (criteria.isOrder()) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(orderByIdAsc());
        }
        if (!criteria.isOrder()) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(orderByIdDesc());
        }
        if (!Strings.isNullOrEmpty(criteria.getName())) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withNom(criteria.getName()));
        }

        if (!Strings.isNullOrEmpty(criteria.getPhone())) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withTel(criteria.getPhone()));
        }

        if (!Strings.isNullOrEmpty(criteria.getLogin())) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withLogin(criteria.getLogin()));
        }
        if (!Strings.isNullOrEmpty(criteria.getEmail())) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withEmail(criteria.getEmail()));
        }
        if (criteria.getActive()!=null) {
            specification = Objects.requireNonNull(Specification.where(specification)).and(withActif(criteria.getActive()));
        }

        return specification;
    }

}

