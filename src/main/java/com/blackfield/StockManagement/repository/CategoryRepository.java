package com.blackfield.StockManagement.repository;


import com.blackfield.StockManagement.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByCode(String code);

    Optional<Category> findByName(String name);
}
