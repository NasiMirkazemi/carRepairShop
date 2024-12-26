package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ItemDto;
import com.first.carrepairshop.entity.Item;
import com.first.carrepairshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                .build());
        itemDto.setItemId(itemEntity.getItemId());
        return itemDto;
    }

    public ItemDto getItem(Integer id) {
        Item itemEntity = itemRepository.findById(id).get();
        return ItemDto.builder()
                .itemId(itemEntity.getItemId())
                .name(itemEntity.getName())
                .type(itemEntity.getType())
                .price(itemEntity.getPrice())
                .qualityLevel(itemEntity.getQualityLevel())
                .build();
    }

    public ItemDto updateItem(ItemDto itemDto) {
        Optional<Item> itemOptional = itemRepository.findById(itemDto.getItemId());
        Item itemEntity = null;
        if (itemOptional.isPresent()) {
            itemEntity = itemOptional.get();
            if (itemDto.getName() != null)
                itemEntity.setName(itemDto.getName());
            if (itemDto.getType() != null)
                itemEntity.setType(itemDto.getType());
            if (itemDto.getPrice() != null)
                itemEntity.setPrice(itemDto.getPrice());
            if (itemDto.getQualityLevel() != null)
                itemEntity.setQualityLevel(itemDto.getQualityLevel());
            itemRepository.save(itemEntity);
        }
        return ItemDto.builder()
                .itemId(itemEntity.getItemId())
                .name(itemEntity.getName())
                .type(itemEntity.getType())
                .price(itemEntity.getPrice())
                .qualityLevel(itemEntity.getQualityLevel())
                .build();

    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }


}
