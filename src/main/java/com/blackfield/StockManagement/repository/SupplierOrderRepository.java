package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.Stock;
import com.blackfield.StockManagement.domain.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long>, JpaSpecificationExecutor<SupplierOrder> {
    Optional<SupplierOrder> findByCode(String code);
}