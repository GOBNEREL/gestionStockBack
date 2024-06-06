package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.criteria.CustomerOrderCriteria;
import com.blackfield.StockManagement.dto.CustomerOrderDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface CustomerOrderService {

    MessageNotification create(CustomerOrderDto dto, String language);

    List<CustomerOrderDto> findByCriteria(CustomerOrderCriteria criteria, String language);

    CustomerOrderDto findByCode(String code , String language);

    CustomerOrderDto findById(Long id , String language);
}