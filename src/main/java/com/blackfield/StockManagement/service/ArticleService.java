package com.blackfield.StockManagement.service;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.util.MessageNotification;

import java.util.List;

public interface ArticleService {

    MessageNotification create(ArticleDto dto, String language);

    List<ArticleDto> findByCriteria(ArticleCriteria criteria, String language);

    ArticleDto findByCode(String code , String language);

    ArticleDto findById(Long id , String language);

}
