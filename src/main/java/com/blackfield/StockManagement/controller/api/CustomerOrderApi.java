package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.CustomerOrderCriteria;
import com.blackfield.StockManagement.dto.CustomerOrderDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("customer-order-api")
public interface CustomerOrderApi {

    @PostMapping(value = Constants.CUSTOMER_ORDER_API + "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification save(@RequestBody CustomerOrderDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.CUSTOMER_ORDER_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CustomerOrderDto> findByCriteria(@RequestBody CustomerOrderCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CUSTOMER_ORDER_API + "/findWithCode")
    CustomerOrderDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CUSTOMER_ORDER_API + "/findWithId")
    CustomerOrderDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}
