package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("article-api")
public interface ArticleApi {

    @PostMapping(value = Constants.ARTICLE_API + "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification save(@RequestBody ArticleDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.ARTICLE_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findByCriteria(@RequestBody ArticleCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.ARTICLE_API + "/findWithCode")
    ArticleDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.ARTICLE_API + "/findWithId")
    ArticleDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}