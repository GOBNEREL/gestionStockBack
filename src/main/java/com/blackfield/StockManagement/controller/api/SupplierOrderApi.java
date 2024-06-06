package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.SupplierOrderCriteria;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("supplier-order-api")
public interface SupplierOrderApi {

    @PostMapping(value = Constants.SUPPLIER_OREDER_API + "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification save(@RequestBody SupplierOrderDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.SUPPLIER_OREDER_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<SupplierOrderDto> findByCriteria(@RequestBody SupplierOrderCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.SUPPLIER_OREDER_API + "/findWithCode")
    SupplierOrderDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.SUPPLIER_OREDER_API + "/findWithId")
    SupplierOrderDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}