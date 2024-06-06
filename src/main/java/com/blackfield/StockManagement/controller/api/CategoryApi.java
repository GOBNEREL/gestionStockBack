package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.CategoryCriteria;
import com.blackfield.StockManagement.dto.CategoryDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api("category-api")
public interface CategoryApi {

    @PostMapping(value = Constants.CATEGORY_API + "/saveCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification saveCategory(@RequestBody CategoryDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.CATEGORY_API + "/findCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findByCategoryCriteria(@RequestBody CategoryCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CATEGORY_API + "/findCategoryWithCode")
    CategoryDto findCategoryWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CATEGORY_API + "/findCategoryWithId")
    CategoryDto findCategoryWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}