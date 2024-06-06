package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Stock;
import com.blackfield.StockManagement.domain.SupplierOrder;
import com.blackfield.StockManagement.dto.StockDto;
import com.blackfield.StockManagement.dto.SupplierOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierOrderMapper extends EntityMapper<SupplierOrderDto, SupplierOrder> {

}
