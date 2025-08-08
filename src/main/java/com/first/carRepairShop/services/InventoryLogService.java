package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.InventoryLogDto;
import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.entity.InventoryLog;

import java.util.List;

public interface InventoryLogService {
    void logStockChange(Inventory inventory, int quantityChanged, String direction, String reason);
    List<InventoryLogDto> getLogsForInventory(Integer inventoryId);

}
