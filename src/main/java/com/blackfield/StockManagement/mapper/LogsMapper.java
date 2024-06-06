package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.LogDomain;
import com.blackfield.StockManagement.dto.LogsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogsMapper extends EntityMapper<LogsDto, LogDomain> {
}