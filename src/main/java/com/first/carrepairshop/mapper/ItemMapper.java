package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.ItemDto;
import com.first.carrepairshop.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto toItemDto(Item item);

    Item toItemEntity(ItemDto itemDto);
}
