package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.InventoryLogDto;
import com.first.carRepairShop.entity.InventoryLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryLogMapper {
    InventoryLogMapper INSTANCE = Mappers.getMapper(InventoryLogMapper.class);

    @Mapping(source = "inventory.inventoryId", target = "inventoryId")
    @Mapping(source = "inventory.item.name", target = "itemName")
    InventoryLogDto toDto(InventoryLog inventoryLog);

    @Mapping(source = "inventoryId", target = "inventory.inventoryId")
    InventoryLog toEntity(InventoryLogDto inventoryLogDto);
}
