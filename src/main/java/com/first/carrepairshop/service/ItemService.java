package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.ItemDto;
import com.first.carrepairshop.entity.Item;
import com.first.carrepairshop.mapper.ItemMapper;
import com.first.carrepairshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemDto addItem(ItemDto itemDto) {
        Item itemEntity = itemRepository.save(itemMapper.toItemEntity(itemDto));
        itemDto.setItemId(itemEntity.getItemId());
        return itemDto;
    }

    public ItemDto getItem(Integer id) {
        Item itemEntity = itemRepository.findById(id).get();
        return itemMapper.toItemDto(itemEntity);
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
        return itemMapper.toItemDto(itemEntity);
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }


}
