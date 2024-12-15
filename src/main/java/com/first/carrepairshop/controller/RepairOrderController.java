package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.RepairOrderDto;
import com.first.carrepairshop.service.RepairOrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repairOrderController")
@AllArgsConstructor
public class RepairOrderController {
    private final RepairOrderService repairOrderService;

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
