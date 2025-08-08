package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.InventoryDto;
import com.first.carRepairShop.dto.InventoryLogDto;
import com.first.carRepairShop.dto.InventoryReportDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto);

    InventoryDto getInventoryByItemId(Integer itemId);

    void decreaseStock(Integer itemId, int quantityUsed, Integer workLogId);

    void increaseStock(Integer itemId, int quantityAdded, String reason);

    boolean isStockAvailable(Integer itemId, int requiredQuantity);

    List<InventoryDto> getItemsBelowMinimumStock();

    List<InventoryDto> getItemsAboveMaximumStock();

    List<InventoryDto> getInventoryByStorageLocation(String location);

    List<InventoryDto> getInventoryBySupplier(String supplier);

    InventoryDto overrideStockQuantity(Integer itemId, int newQuantity, String note);

    List<InventoryLogDto> generateInventoryReport();

    List<InventoryLogDto> getLogsForInventory(Integer inventoryId);

    void deleteInventoryByItemId(Integer itemId);

}
