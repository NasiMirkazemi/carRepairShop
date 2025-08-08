package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.UserDto;
import com.first.carRepairShop.entity.UserApp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDTo(UserApp userApp);

    UserApp toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<UserApp> userAppList);

    List<UserApp> toEntityList(List<UserDto> userDtoList);
}
