package com.blackfield.StockManagement.repository;

import com.blackfield.StockManagement.domain.LogDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogsRepository extends JpaRepository<LogDomain, Long>, JpaSpecificationExecutor<LogDomain> {
}