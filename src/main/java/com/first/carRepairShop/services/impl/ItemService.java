package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.ItemDto;
import com.first.carRepairShop.entity.Item;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.ItemMapper;
import com.first.carRepairShop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public ItemDto addItem(ItemDto itemDto) {
        if (itemDto == null) {
            throw new IllegalArgumentException("ItemDto cannot be null");
        }
        Item item = itemRepository.save(itemMapper.toItemEntity(itemDto));
        return itemMapper.toItemDto(item);
    }

    public ItemDto getItem(Integer itemId) {
        if (itemId == null)
            throw new BadRequestException("Item Id is required");
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("No Item found with id:" + itemId));
        return itemMapper.toItemDto(itemEntity);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        if (itemDto == null)
            throw new BadRequestException("item can not be null");
        Item item = itemRepository.findById(itemDto.getItemId())
                .orElseThrow(() -> new NotFoundException("No item found with id:" + itemDto.getItemId()));
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getType()).ifPresent(item::setType);
        Optional.ofNullable(itemDto.getBrand()).ifPresent(item::setBrand);
        Item updatedItem = itemRepository.save(item);
        return itemMapper.toItemDto(updatedItem);
    }


    public void deleteItem(Integer itemId) {
        if (itemId == null)
            throw new BadRequestException("item id is required");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("No item found with id:" + itemId));
        itemRepository.delete(item);
        logger.info("Item with id { } has been deleted", item.getItemId());

    }


}

