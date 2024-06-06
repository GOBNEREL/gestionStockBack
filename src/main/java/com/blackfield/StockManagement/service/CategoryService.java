package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface CategoryService {

    MessageNotification createCategory(CategoryDto dto, String language);

    List<CategoryDto> findCategoryByCriteria(CategoryCriteria criteria, String language);

    CategoryDto findByCode(String code , String language);

    CategoryDto findById(Long id , String language);

}
