package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.Customer;
import com.blackfield.StockManagement.domain.Supplier;
import com.blackfield.StockManagement.dto.CustomerDto;
import com.blackfield.StockManagement.dto.SupplierDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper extends EntityMapper<SupplierDto, Supplier> {

}