package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.SupplierApi;
import com.blackfield.StockManagement.criteria.SupplierCriteria;
import com.blackfield.StockManagement.dto.SupplierDto;
import com.blackfield.StockManagement.service.SupplierService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;

    @Override
    public MessageNotification save(SupplierDto dto, String language) {
        return supplierService.create(dto, language);
    }

    @Override
    public List<SupplierDto> findByCriteria(SupplierCriteria criteria, String language) {
        return supplierService.findByCriteria(criteria, language);
    }

    @Override
    public SupplierDto findWithCode(String code, String language) {
        return supplierService.findByCode(code, language);
    }

    @Override
    public SupplierDto findWithId(Long id, String language) {
        return supplierService.findById(id, language);
    }
}
