package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.CustomerApi;
import com.blackfield.StockManagement.criteria.CustomerCriteria;
import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.service.CustomerService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public MessageNotification save(CustomerDto dto, String language) {
        return customerService.create(dto, language);
    }

    @Override
    public List<CustomerDto> findByCriteria(CustomerCriteria criteria, String language) {
        return customerService.findByCriteria(criteria, language);
    }

    @Override
    public CustomerDto findWithCode(String code, String language) {
        return customerService.findByCode(code, language);
    }

    @Override
    public CustomerDto findWithId(Long id, String language) {
        return customerService.findById(id, language);
    }
}
