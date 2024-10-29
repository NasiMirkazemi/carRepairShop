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
    public ResponseEntity<RepairOrderDto> add(@RequestBody RepairOrderDto repairOrderDto){
        return ResponseEntity.ok(repairOrderService.add(repairOrderDto));
    }
    @PatchMapping("/patch")
    public ResponseEntity<RepairOrderDto> updateByPatch(@RequestBody RepairOrderDto repairOrderDto){
        return ResponseEntity.ok(repairOrderService.updateByPatch(repairOrderDto));
    }
}
