package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.RolesDomain;
import com.blackfield.StockManagement.dto.RolesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolesMapper extends EntityMapper<RolesDto, RolesDomain> {
}