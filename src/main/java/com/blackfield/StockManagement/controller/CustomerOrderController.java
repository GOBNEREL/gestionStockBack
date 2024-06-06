package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.CustomerOrderApi;
import com.blackfield.StockManagement.criteria.CustomerOrderCriteria;
import com.blackfield.StockManagement.dto.CustomerOrderDto;
import com.blackfield.StockManagement.service.CustomerOrderService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerOrderController implements CustomerOrderApi {

    private final CustomerOrderService customerOrderService;

    @Override
    public MessageNotification save(CustomerOrderDto dto, String language) {
        return customerOrderService.create(dto, language);
    }

    @Override
    public List<CustomerOrderDto> findByCriteria(CustomerOrderCriteria criteria, String language) {
        return customerOrderService.findByCriteria(criteria, language);
    }

    @Override
    public CustomerOrderDto findWithCode(String code, String language) {
        return customerOrderService.findByCode(code, language);
    }

    @Override
    public CustomerOrderDto findWithId(Long id, String language) {
        return customerOrderService.findById(id, language);
    }
}