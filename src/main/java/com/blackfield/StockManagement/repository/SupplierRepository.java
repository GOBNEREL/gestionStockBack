package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.Customer;
import com.blackfield.StockManagement.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    Optional<Supplier> findByCode(String code);

    Optional<Supplier> findByName(String name);
}
