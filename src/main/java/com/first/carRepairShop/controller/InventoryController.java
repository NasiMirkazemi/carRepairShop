package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.InventoryDto;
import com.first.carRepairShop.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<InventoryDto> createInventory(@RequestBody @Valid InventoryDto inventoryDto) {
        InventoryDto inventoryDto1 = inventoryService.createInventory(inventoryDto);
        return ResponseEntity.ok(inventoryDto1);
    }

    @GetMapping("/getByItem/{itemId}")
    public ResponseEntity<InventoryDto> getInventoryByItemId(@PathVariable("itemId") Integer itemId) {
        InventoryDto inventoryDto = inventoryService.getInventoryByItemId(itemId);
        return ResponseEntity.ok(inventoryDto);
    }

    @PutMapping("/decrease")
    public ResponseEntity<String> decreaseStock(
            @RequestParam("itemId") Integer itemId
            , @RequestParam("quantityUsed") int quantityUsed
            , @RequestParam("workLogId") Integer workLogId) {
        inventoryService.decreaseStock(itemId, quantityUsed, workLogId);
        String message = String.format("Inventory for Item id: %d successfully decreased by %d units for Work Log id: %d"
                , itemId
                , quantityUsed
                , workLogId);
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/increase")
    public ResponseEntity<String> increaseStock(
            @RequestParam("itemId") Integer itemId
            , @RequestParam("quantityUsed") int quantityUsed
            , String reason) {
        inventoryService.increaseStock(itemId, quantityUsed, reason);
        String message = String.format("Inventory for Item id: %d successfully increased by %d .Reason :%s"
                , itemId
                , quantityUsed
                , reason);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/check-stock")
    public ResponseEntity<Boolean> isStockAvailable(@RequestParam("itemId") Integer itemId
            , @RequestParam("requiredQuantity") int requiredQuantity) {
        boolean isStockAvailable = inventoryService.isStockAvailable(itemId, requiredQuantity);
        return ResponseEntity.ok(isStockAvailable);
    }

    @GetMapping("/blewMinimum")
    public ResponseEntity<List<InventoryDto>> getItemsBelowMinimumStock() {
        List<InventoryDto> inventoryDtoList = inventoryService.getItemsBelowMinimumStock();
        return ResponseEntity.ok(inventoryDtoList);
    }

    @GetMapping("/aboveMaximumStock")
    public ResponseEntity<List<InventoryDto>> getItemsAboveMaximumStock() {
        List<InventoryDto> inventoryDtoList = inventoryService.getItemsAboveMaximumStock();
        return ResponseEntity.ok(inventoryDtoList);
    }

    @GetMapping("/getByLocation")
    public ResponseEntity<List<InventoryDto>> getInventoryByStorageLocation(@RequestParam("location") String location) {
        List<InventoryDto> inventoryDtoList = inventoryService.getInventoryByStorageLocation(location);
        return ResponseEntity.ok(inventoryDtoList);
    }

    @GetMapping("/getBySupplier")
    public ResponseEntity<List<InventoryDto>> getInventoryBySupplier(@RequestParam("supplier") String supplier) {
        List<InventoryDto> inventoryDtoList = inventoryService.getInventoryBySupplier(supplier);
        return ResponseEntity.ok(inventoryDtoList);
    }

    @PutMapping("/override")
    public ResponseEntity<InventoryDto> overrideStockQuantity(@RequestParam("itemId") Integer itemId
            , @RequestParam("newQuantity") int newQuantity
            , @RequestParam(value = "note", required = false) String note) {
        InventoryDto inventoryDto = inventoryService.overrideStockQuantity(itemId, newQuantity, note);
        return ResponseEntity.ok(inventoryDto);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> deleteInventoryByItemId(@PathVariable("itemId") Integer itemId) {
        inventoryService.deleteInventoryByItemId(itemId);
        return ResponseEntity.ok().body("Inventory has been deleted for item with id :" + itemId);
    }


}
