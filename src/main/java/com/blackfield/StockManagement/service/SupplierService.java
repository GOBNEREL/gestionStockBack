package com.blackfield.StockManagement.service;


import com.blackfield.StockManagement.criteria.SupplierCriteria;
import com.blackfield.StockManagement.dto.SupplierDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface SupplierService {

    MessageNotification create(SupplierDto dto, String language);

    List<SupplierDto> findByCriteria(SupplierCriteria criteria, String language);

    SupplierDto findByCode(String code , String language);

    SupplierDto findById(Long id , String language);

}
