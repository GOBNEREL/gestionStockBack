package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.ArticleCriteria;
import com.blackfield.StockManagement.criteria.CustomerCriteria;
import com.blackfield.StockManagement.dto.ArticleDto;
import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("customer-api")
public interface CustomerApi {

    @PostMapping(value = Constants.CUSTOMER_API + "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MessageNotification save(@RequestBody CustomerDto dto, @RequestParam(value = "language", required = false) String language);

    @PostMapping(value = Constants.CUSTOMER_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CustomerDto> findByCriteria(@RequestBody CustomerCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CUSTOMER_API + "/findWithCode")
    CustomerDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.CUSTOMER_API + "/findWithId")
    CustomerDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}
