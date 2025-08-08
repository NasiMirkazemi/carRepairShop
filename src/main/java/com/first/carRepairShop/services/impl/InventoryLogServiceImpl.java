package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.InventoryLogDto;
import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.entity.InventoryLog;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.InventoryLogMapper;
import com.first.carRepairShop.repository.InventoryLogRepository;
import com.first.carRepairShop.services.InventoryLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryLogServiceImpl implements InventoryLogService {
    private final InventoryLogRepository inventoryLogRepository;
    private final InventoryLogMapper inventoryLogMapper;


    public void logStockChange(Inventory inventory, int quantityChanged, String direction, String reason) {
        if (inventory == null)
            throw new BadRequestException("Inventory is required");
        if (quantityChanged == 0) return;

        if (direction == null) throw new BadRequestException("Direction is required");

        if (!direction.equals("IN") && !direction.equals("OUT") && !direction.equals("OVERRIDE") && !direction.equals("CREATE")) {
            throw new IllegalArgumentException("Direction must be 'IN' or 'OUT' or 'OVERRIDE' or 'CREATE'");
        }
        if (reason == null) throw new BadRequestException("reason is required");
        InventoryLog inventoryLog = InventoryLog.builder()
                .inventory(inventory)
                .quantityChanged(quantityChanged)
                .direction(direction)
                .reason(reason)
                .build();
        inventoryLogRepository.save(inventoryLog);
    }

    public List<InventoryLogDto> getLogsForInventory(Integer inventoryId) {
        if (inventoryId == null) throw new BadRequestException("Inventory id is required");
        List<InventoryLog> inventoryLogs = inventoryLogRepository.findAllByInventory_InventoryId(inventoryId);
        if (inventoryLogs.isEmpty()) {
            throw new NotFoundException("No logs found for inventory id: " + inventoryId);
        }
        return inventoryLogs.stream()
                .map(inventoryLogMapper::toDto)
                .collect(Collectors.toList());
    }

}

