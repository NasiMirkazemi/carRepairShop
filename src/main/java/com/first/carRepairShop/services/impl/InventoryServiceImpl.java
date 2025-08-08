package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.InventoryDto;
import com.first.carRepairShop.dto.InventoryLogDto;
import com.first.carRepairShop.dto.InventoryReportDto;
import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.entity.InventoryLog;
import com.first.carRepairShop.entity.Item;
import com.first.carRepairShop.entity.WorkLog;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.InventoryMapper;
import com.first.carRepairShop.repository.InventoryRepository;
import com.first.carRepairShop.repository.ItemRepository;
import com.first.carRepairShop.repository.WorkLogRepository;
import com.first.carRepairShop.services.InventoryLogService;
import com.first.carRepairShop.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ItemRepository itemRepository;
    private final WorkLogRepository workLogRepository;
    private final InventoryLogService inventoryLogService;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        if (inventoryDto == null) throw new BadRequestException("Inventory dto cannot be null");
        if (inventoryDto.getQuantity() < 0) throw new BadRequestException("Initial quantity must be zero or more");
        if (inventoryDto.getMinimumStockLevel() < 0 || inventoryDto.getMaximumStockLevel() < 0)
            throw new BadRequestException("Min and max stock must be non-negative");
        Item item = itemRepository.findById(inventoryDto.getItemId())
                .orElseThrow(() -> new NotFoundException("No Item found with id :" + inventoryDto.getItemId()));
        if (inventoryRepository.findInventoryByItem(inventoryDto.getItemId()).isPresent()) {
            throw new BadRequestException("Inventory already exist for item id: " + inventoryDto.getItemId());
        }
        Inventory inventory = inventoryMapper.toEntity(inventoryDto);
        inventory.setItem(item);
        Inventory savedInventory = inventoryRepository.save(inventory);
        inventoryLogService.logStockChange(inventory, inventory.getQuantity(), "CREATE", "Initial stock created");
        log.info("Created inventory for item {} with quantity {}", inventoryDto.getItemId(), inventoryDto.getQuantity());
        return inventoryMapper.toDto(savedInventory);

    }

    @Override
    public InventoryDto getInventoryByItemId(Integer itemId) {
        if (itemId == null)
            throw new BadRequestException(" Item Id is required");
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException(" No Inventory found for this Item"));
        return inventoryMapper.toDto(inventory);

    }

    @Override
    public void decreaseStock(Integer itemId, int quantityUsed, Integer workLogId) {
        if (itemId == null)
            throw new BadRequestException(" Item id is required");
        if (quantityUsed <= 0)
            throw new BadRequestException(" Used quantity can't be zero");
        if (workLogId == null)
            throw new BadRequestException(" Work log id is required");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("No Item found with id :" + itemId));
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work log is found with id :" + workLogId));
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException("No Inventory found for Item with id:" + item));
        if (inventory.getQuantity() < quantityUsed) {
            throw new BadRequestException("Not enough stock available : " + inventory.getQuantity());
        }
        inventory.setQuantity(inventory.getQuantity() - quantityUsed);
        Inventory savedInventory = inventoryRepository.save(inventory);
        inventoryLogService.logStockChange(savedInventory, quantityUsed, "OUT", "Used for work log id:" + workLog);
        log.info("Decreased stock item {} by {} units for Work log {}", itemId, quantityUsed, workLog.getWorkLogId());
    }

    @Override
    public void increaseStock(Integer itemId, int quantityAdded, String reason) {
        if (itemId == null)
            throw new BadRequestException("Item id is required");
        if (quantityAdded <= 0)
            throw new BadRequestException("Added quantity must be greater than zero");
        if (reason == null || reason.isBlank())
            throw new BadRequestException(" reason is required");
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException(" No Inventory found for Item with id :" + itemId));
        inventory.setQuantity(inventory.getQuantity() + quantityAdded);
        Inventory savedInventory = inventoryRepository.save(inventory);
        inventoryLogService.logStockChange(savedInventory, quantityAdded, "IN", reason);
        log.info("Increased stock item {} by {} units. Reason: {}", itemId, quantityAdded, reason);
    }

    @Override
    public boolean isStockAvailable(Integer itemId, int requiredQuantity) {
        if (itemId == null)
            throw new BadRequestException(" Item id is required");
        if (requiredQuantity <= 0)
            throw new BadRequestException("required quantity  must be greater than zero");
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException("No inventory found for Item by id: " + itemId));
        log.info("Checking stock for item {}: required = {}, available = {}", itemId, requiredQuantity, inventory.getQuantity());
        return inventory.getQuantity() >= requiredQuantity;
    }

    @Override
    public List<InventoryDto> getItemsBelowMinimumStock() {
        List<Inventory> blowMinimumLevelList = inventoryRepository.findItemBlowMinimumStock()
                .orElseThrow(() -> new NotFoundException("No inventory items found below minimum stock level"));
        return inventoryMapper.toInventoryDtoList(blowMinimumLevelList);
    }

    @Override
    public List<InventoryDto> getItemsAboveMaximumStock() {
        List<Inventory> aboveMaximumLevelList = inventoryRepository.findItemsAboveMaximumStock()
                .orElseThrow(() -> new NotFoundException("No Inventory found above Maximum stock level"));
        return inventoryMapper.toInventoryDtoList(aboveMaximumLevelList);
    }

    @Override
    public List<InventoryDto> getInventoryByStorageLocation(String location) {
        if (location == null)
            throw new BadRequestException("Location is required");
        List<Inventory> inventoryList = inventoryRepository.findInventoryByStorageLocation(location)
                .orElseThrow(() -> new NotFoundException("No Inventory found for location :" + location));
        return inventoryMapper.toInventoryDtoList(inventoryList);
    }

    @Override
    public List<InventoryDto> getInventoryBySupplier(String supplier) {
        if (supplier == null)
            throw new BadRequestException("Supplier is required");
        List<Inventory> inventoryList = inventoryRepository.findInventoryBySupplier(supplier)
                .orElseThrow(() -> new NotFoundException("No Inventory gound for this Supplier :" + supplier));
        return inventoryMapper.toInventoryDtoList(inventoryList);
    }

    @Override
    public InventoryDto overrideStockQuantity(Integer itemId, int newQuantity, String note) {
        if (itemId == null)
            throw new BadRequestException("Item id is required");
        if (note == null)
            throw new BadRequestException("Note is required");
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException("No Inventory found for Item by id :" + itemId));
        int oldQuantity = inventory.getQuantity();
        inventory.setQuantity(newQuantity);
        inventory.setNote(note);
        Inventory savedInventory = inventoryRepository.save(inventory);
        int quantityChanged = Math.abs(newQuantity - oldQuantity);
        String direction;
        if (newQuantity > oldQuantity) {
            direction = "IN";
        } else if (newQuantity < oldQuantity) {
            direction = "OUT";
        } else {
            direction = "OVERRIDE";
        }
        inventoryLogService.logStockChange(savedInventory, quantityChanged, direction, note);
        return inventoryMapper.toDto(inventory);
    }


    @Override
    public List<InventoryLogDto> generateInventoryReport() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryLogDto> allLogs = new ArrayList<>();
        for (Inventory inventory : inventoryList) {
            List<InventoryLogDto> inventoryLogDtoList = inventoryLogService.getLogsForInventory(inventory.getInventoryId());
            allLogs.addAll(inventoryLogDtoList);
        }
        return allLogs;
    }

    @Override
    public List<InventoryLogDto> getLogsForInventory(Integer inventoryId) {
        if (inventoryId == null) throw new BadRequestException("Inventory id is required");
        List<InventoryLogDto> inventoryLogDtoList = inventoryLogService.getLogsForInventory(inventoryId);
        if (inventoryLogDtoList.isEmpty()) {
            throw new NotFoundException("No Logs found for Inventory with id: " + inventoryId);
        }
        return inventoryLogDtoList;
    }


    @Override
    public void deleteInventoryByItemId(Integer itemId) {
        if (itemId == null) throw new BadRequestException("Item id is required");
        Inventory inventory = inventoryRepository.findInventoryByItem(itemId)
                .orElseThrow(() -> new NotFoundException("No inventory found for item with id :" + itemId));
        inventory.setDeleted(true);
        inventoryRepository.save(inventory);
        log.info("Soft deleted inventory with ID {} for item ID {}", inventory.getInventoryId(), itemId);
    }
}
