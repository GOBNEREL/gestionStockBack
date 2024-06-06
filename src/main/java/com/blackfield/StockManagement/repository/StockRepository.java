package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {
    Optional<Stock> findByCode(String code);

    Optional<Stock> findByName(String name);
}
