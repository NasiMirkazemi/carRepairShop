package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.service.MechanicService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanicController")
//@RequiredArgsConstructor

public class MechanicController {
    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }


    @PostMapping("/add")
    public ResponseEntity<MechanicDto> addMechanic(@RequestBody MechanicDto mechanicDto) {
        return ResponseEntity.ok(mechanicService.addMechanic(mechanicDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<MechanicDto> getMechanic(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mechanicService.getMechanic(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<MechanicDto> updateMechanic(@RequestBody MechanicDto mechanicDto) {
        return ResponseEntity.ok(mechanicService.updateMechanic(mechanicDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMechanic(@PathVariable("id") Integer id) {
        mechanicService.deleteMechanic(id);
        return ResponseEntity.ok("mechanic whit id" + id + " is deleted");
    }
}
