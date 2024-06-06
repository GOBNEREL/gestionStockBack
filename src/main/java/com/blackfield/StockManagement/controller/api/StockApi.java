package com.blackfield.StockManagement.controller.api;

import com.blackfield.StockManagement.criteria.StockCriteria;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.util.Constants;
import com.blackfield.StockManagement.util.MessageNotification;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("stock-api")
public interface StockApi {

    @PostMapping(value = Constants.STOCK_API + "/findByCriteria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<StockDto> findByCriteria(@RequestBody StockCriteria criteria, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.STOCK_API + "/findWithCode")
    StockDto findWithCode(@RequestParam(value = "code") String code, @RequestParam(value = "language", required = false) String language);

    @GetMapping(value = Constants.STOCK_API + "/findWithId")
    StockDto findWithId(@RequestParam(value = "id") Long id, @RequestParam(value = "language", required = false) String language);
}
