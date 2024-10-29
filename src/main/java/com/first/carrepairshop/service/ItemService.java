package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ItemDto;
import com.first.carrepairshop.entity.Item;
import com.first.carrepairshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemDto addItem(ItemDto itemDto) {
        Item itemEntity = itemRepository.save(Item.builder()
                .name(itemDto.getName())
                .type(itemDto.getType())
                .price(itemDto.getPrice())
                .qualityLevel(itemDto.getQualityLevel())
                .repairOrders(itemDto.getRepairOrders())
                .build());
        itemDto.setItemId(itemEntity.getItemId());
        return itemDto;
    }

    public ItemDto update(ItemDto itemDto) {
         Item itemEntity=itemRepository.findById(itemDto.getItemId()).get();
         itemEntity.setName(itemDto.getName());
         itemEntity.setType(itemDto.getType());
         itemEntity.setPrice(itemDto.getPrice());
         itemEntity.setQualityLevel(itemDto.getQualityLevel());
         itemEntity.setRepairOrders(itemDto.getRepairOrders());
         itemRepository.save(itemEntity);
         return ItemDto.builder()
                 .itemId(itemEntity.getItemId())
                 .name(itemEntity.getName())
                 .type(itemEntity.getType())
                 .price(itemEntity.getPrice())
                 .qualityLevel(itemEntity.getQualityLevel())
                 .repairOrders(itemEntity.getRepairOrders())
                 .build();

    }

    public String removeItemById(Integer id) {
        itemRepository.deleteById(id);
         return "item " + id + " deleted";
    }

    public ItemDto getById(Integer id) {
        Item itemEntity = itemRepository.findById(id).get();
        return ItemDto.builder()
                .itemId(itemEntity.getItemId())
                .name(itemEntity.getName())
                .type(itemEntity.getType())
                .price(itemEntity.getPrice())
                .qualityLevel(itemEntity.getQualityLevel())
                .repairOrders(itemEntity.getRepairOrders())
                .build();
    }
}
