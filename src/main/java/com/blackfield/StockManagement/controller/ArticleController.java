package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.ArticleApi;
import com.blackfield.StockManagement.controller.api.CategoryApi;
import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.service.ArticleService;
import com.blackfield.StockManagement.service.CategoryService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;

    @Override
    public MessageNotification save(ArticleDto dto, String language) {
        return articleService.create(dto, language);
    }

    @Override
    public List<ArticleDto> findByCriteria(ArticleCriteria criteria, String language) {
        return articleService.findByCriteria(criteria, language);
    }

    @Override
    public ArticleDto findWithCode(String code, String language) {
        return articleService.findByCode(code, language);
    }

    @Override
    public ArticleDto findWithId(Long id, String language) {
        return articleService.findById(id, language);
    }
}