package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.CustomerOrder;
import com.blackfield.StockManagement.domain.SupplierOrder;
import com.blackfield.StockManagement.dto.CustomerOrderDto;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper extends EntityMapper<CustomerOrderDto, CustomerOrder> {

}
