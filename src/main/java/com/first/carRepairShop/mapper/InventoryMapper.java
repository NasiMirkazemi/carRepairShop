package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.InventoryDto;
import com.first.carRepairShop.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface InventoryMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "itemId", source = "item.itemId")
    InventoryDto toDto(Inventory inventory);

    @Mapping(target = "item.itemId", source = "itemId")
    Inventory toEntity(InventoryDto inventoryDto);

    List<InventoryDto> toInventoryDtoList(List<Inventory> inventoryList);

    List<Inventory> toInventoryEntityList(List<InventoryDto> inventoryDtoList);


}
