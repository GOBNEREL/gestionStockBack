package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Optional<Article> findByCode(String code);

    Optional<Article> findByName(String name);
}