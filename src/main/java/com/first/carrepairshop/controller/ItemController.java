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
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto){
        return ResponseEntity.ok(itemService.addItem(itemDto));
    }
    @GetMapping("/get/{id}"  )
    public ResponseEntity<ItemDto> getItem(@PathVariable("id") Integer id){
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto){
        return ResponseEntity.ok(itemService.updateItem(itemDto));
    }
    @DeleteMapping("/delete/{id}"  )
    public ResponseEntity<String> deleteItem(@PathVariable("id") Integer id){
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item whit id:"+id+"is deleted");

    }
}
