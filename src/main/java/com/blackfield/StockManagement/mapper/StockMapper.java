package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.domain.Stock;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.StockDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper extends EntityMapper<StockDto, Stock> {

}
