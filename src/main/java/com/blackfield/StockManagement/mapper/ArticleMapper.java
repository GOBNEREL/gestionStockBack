package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Article;
import com.blackfield.StockManagement.dto.ArticleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDto, Article> {

}