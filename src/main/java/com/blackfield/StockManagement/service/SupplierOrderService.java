package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.criteria.SupplierOrderCriteria;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface SupplierOrderService {

    MessageNotification create(SupplierOrderDto dto, String language);

    List<SupplierOrderDto> findByCriteria(SupplierOrderCriteria criteria, String language);

    SupplierOrderDto findByCode(String code , String language);

    SupplierOrderDto findById(Long id , String language);
}