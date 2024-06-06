package com.blackfield.StockManagement.controller;

import com.blackfield.StockManagement.controller.api.SupplierOrderApi;
import com.blackfield.StockManagement.criteria.SupplierOrderCriteria;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import com.blackfield.StockManagement.service.SupplierOrderService;
import com.blackfield.StockManagement.util.MessageNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupplierOrderController implements SupplierOrderApi {

    private final SupplierOrderService supplierOrderService;

    @Override
    public MessageNotification save(SupplierOrderDto dto, String language) {
        return supplierOrderService.create(dto, language);
    }

    @Override
    public List<SupplierOrderDto> findByCriteria(SupplierOrderCriteria criteria, String language) {
        return supplierOrderService.findByCriteria(criteria, language);
    }

    @Override
    public SupplierOrderDto findWithCode(String code, String language) {
        return supplierOrderService.findByCode(code, language);
    }

    @Override
    public SupplierOrderDto findWithId(Long id, String language) {
        return supplierOrderService.findById(id, language);
    }
}
