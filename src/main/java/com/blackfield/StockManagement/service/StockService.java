package com.blackfield.StockManagement.service;


import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface StockService {

    List<StockDto> findByCriteria(StockCriteria criteria, String language);

    StockDto findByCode(String code , String language);

    StockDto findById(Long id , String language);

}
