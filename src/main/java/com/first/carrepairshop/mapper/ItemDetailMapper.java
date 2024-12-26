package com.first.carrepairshop.mapper;

import com.first.carrepairshop.associations.ItemDetail;
import com.first.carrepairshop.dto.ItemDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemDetailMapper {
    ItemDetailMapper INSTANCE = Mappers.getMapper(ItemDetailMapper.class);

    ItemDetailDto toItemDetailDto(ItemDetail itemDetail);

    ItemDetail toItemDetailEntity(ItemDetailDto itemDetailDto);
}