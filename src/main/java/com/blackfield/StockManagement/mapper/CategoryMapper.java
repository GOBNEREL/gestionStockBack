package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Category;
import com.blackfield.StockManagement.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {

}
