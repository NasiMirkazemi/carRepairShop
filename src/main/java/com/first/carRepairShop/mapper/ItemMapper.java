package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.ItemDto;
import com.first.carRepairShop.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto toItemDto(Item item);

    Item toItemEntity(ItemDto itemDto);
}
