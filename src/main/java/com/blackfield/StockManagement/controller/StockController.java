package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.ArticleApi;

import com.blackfield.StockManagement.controller.api.StockApi;
import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.service.StockService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController implements StockApi {

    private final StockService stockService;

    @Override
    public List<StockDto> findByCriteria(StockCriteria criteria, String language) {
        return stockService.findByCriteria(criteria, language);
    }

    @Override
    public StockDto findWithCode(String code, String language) {
        return stockService.findByCode(code, language);
    }

    @Override
    public StockDto findWithId(Long id, String language) {
        return stockService.findById(id, language);
    }
}
