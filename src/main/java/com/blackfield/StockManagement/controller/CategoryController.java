package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.CategoryApi;
import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.service.CategoryService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public MessageNotification saveCategory(CategoryDto dto, String language) {
        return categoryService.createCategory(dto, language);
    }

    @Override
    public List<CategoryDto> findByCategoryCriteria(CategoryCriteria criteria, String language) {
        return categoryService.findCategoryByCriteria(criteria, language);
    }

    @Override
    public CategoryDto findCategoryWithCode(String code, String language) {
        return categoryService.findByCode(code, language);
    }

    @Override
    public CategoryDto findCategoryWithId(Long id, String language) {
        return categoryService.findById(id, language);
    }
}