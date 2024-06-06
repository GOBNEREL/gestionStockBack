package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByCode(String code);

    Optional<Customer> findByName(String name);
}