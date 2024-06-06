package com.blackfield.StockManagement.controller.api;


import com.blackfield.StockManagement.criteria.SupplierCriteria;
import com.blackfield.StockManagement.dto.SupplierDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("supplier-api")
public interface SupplierApi {

    @PostMapping(value = Constants.SUPPLIER_API + "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification save(@RequestBody SupplierDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.SUPPLIER_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<SupplierDto> findByCriteria(@RequestBody SupplierCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.SUPPLIER_API + "/findWithCode")
    SupplierDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.SUPPLIER_API + "/findWithId")
    SupplierDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}
