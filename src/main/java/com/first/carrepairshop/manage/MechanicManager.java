package com.first.carrepairshop.manage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/mechanic")
public class MechanicManager {

    @GetMapping
    public ResponseEntity<String> echo(@RequestParam String msg){
        return ResponseEntity.ok(msg);
    }


}
