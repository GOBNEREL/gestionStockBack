package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.domain.Customer;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {

}
