package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.ItemDto;
import com.first.carrepairshop.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itemController")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping("/add")
    public ResponseEntity<ItemDto> add(@RequestBody ItemDto itemDto){
        return ResponseEntity.ok(itemService.addItem(itemDto));
    }
    @GetMapping("/get/{itemId}"  )
    public ResponseEntity<ItemDto> get(@PathVariable("itemId") Integer id){
        return ResponseEntity.ok(itemService.getById(id));
    }
    @DeleteMapping("/delete/{itemId}"  )
    public ResponseEntity<?> delete(@PathVariable("itemId") Integer id){
        return ResponseEntity.ok(itemService.removeItemById(id));

    }
    @PutMapping("/update")
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto itemDto){
        return ResponseEntity.ok(itemService.update(itemDto));
    }
}
