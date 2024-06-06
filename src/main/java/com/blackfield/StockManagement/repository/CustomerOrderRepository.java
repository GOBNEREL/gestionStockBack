package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.CustomerOrder;
import com.blackfield.StockManagement.domain.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor<CustomerOrder> {
    Optional<CustomerOrder> findByCode(String code);
}