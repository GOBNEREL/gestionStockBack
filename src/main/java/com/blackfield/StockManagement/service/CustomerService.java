package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.criteria.CustomerCriteria;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface CustomerService {

    MessageNotification create(CustomerDto dto, String language);

    List<CustomerDto> findByCriteria(CustomerCriteria criteria, String language);

    CustomerDto findByCode(String code , String language);

    CustomerDto findById(Long id , String language);

}
