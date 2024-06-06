package com.blackfield.StockManagement.mapper;

import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UsersDomain> {
}

