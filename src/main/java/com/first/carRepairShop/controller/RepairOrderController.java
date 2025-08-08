package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.RepairOrderDto;
import com.first.carRepairShop.services.impl.RepairOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repairOrder")
@RequiredArgsConstructor
public class RepairOrderController {
    private final RepairOrderServiceImpl repairOrderService;


    @PostMapping("/add")
    public ResponseEntity<RepairOrderDto> addRepairOrder(@RequestBody RepairOrderDto repairOrderDto) {
        return ResponseEntity.ok(repairOrderService.addRepairOrder(repairOrderDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RepairOrderDto> getRepairOrder(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(repairOrderService.getRepairOrder(id));
    }

    @PatchMapping("/patch")
    public ResponseEntity<RepairOrderDto> updateRepairOrder(@RequestBody RepairOrderDto repairOrderDto) {
        return ResponseEntity.ok(repairOrderService.updateRepairOrder(repairOrderDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRepairOrder(@PathVariable("id") Integer id) {
        repairOrderService.deleteRepairOrder(id);
        return ResponseEntity.ok("repairOrder whit id:" + id + "is deleted");
    }
}
