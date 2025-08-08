package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.MechanicDto;
import com.first.carRepairShop.dto.MechanicRegister;
import com.first.carRepairShop.services.MechanicService;
import com.first.carRepairShop.services.impl.MechanicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanic")
@RequiredArgsConstructor

public class MechanicController {
    private final MechanicService mechanicService;

    @PostMapping("/add")
    public ResponseEntity<MechanicRegister> add(@RequestBody MechanicRegister mechanicRegister) {
        MechanicRegister savedMechanic = mechanicService.addMechanic(mechanicRegister);
        return ResponseEntity.ok(savedMechanic);
    }

    @DeleteMapping("/delete/{mechanicId}")
    public ResponseEntity<String> delete(@PathVariable("mechanicId") Integer mechanicId) {
        mechanicService.deleteMechanic(mechanicId);
        return ResponseEntity.ok().body("Mechanic  has been deleted with id : " + mechanicId);

    }


}
