package com.first.carRepairShop.mapper;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ItemDto;
import com.first.carRepairShop.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ItemDetailMapper {
    ItemDetailMapper INSTANCE = Mappers.getMapper(ItemDetailMapper.class);

    // ✅ Convert Item → ItemDetail
    @Mapping( target = "itemId",source = "itemId")
    @Mapping( target = "itemName",source = "name")
    ItemDetail toItemDetail(Item item);

    // ✅ Convert ItemDetail → Item
    @Mapping(source = "itemId", target = "itemId")
    @Mapping(source = "itemName", target = "name")
    @Mapping(target = "brand", ignore = true)
    Item toItem(ItemDetail itemDetail);

    // ✅ Convert List<Item> → List<ItemDetail>
    List<ItemDetail> toItemDetailListFromItem(List<Item> itemList);

    // ✅ Convert List<ItemDetail> → List<Item>
    List<Item> toItemListFromItemDetail(List<ItemDetail> itemDetailList);

    // ✅ Convert ItemDto → ItemDetailDto
    @Mapping(source = "itemId", target = "itemId")
    @Mapping(source = "name", target = "itemName")
    ItemDetailDto toItemDetailDto(ItemDto itemDto);

    // ✅ Convert ItemDetailDto → ItemDto
    @Mapping(source = "itemId", target = "itemId")
    @Mapping(source = "itemName", target = "name")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "brand", ignore = true)
    ItemDto toItemDto(ItemDetailDto itemDetailDto);

    // ✅ Convert List<ItemDto> → List<ItemDetailDto>
    List<ItemDetailDto> toItemDetailDtoListFromItemDto(List<ItemDto> itemDtoList);

    // ✅ Convert List<ItemDetailDto> → List<ItemDto>
    List<ItemDto> toItemDtoListFromItemDetailDto(List<ItemDetailDto> itemDetailDtoList);

    // ✅ Convert ItemDetailDto → ItemDetail
    ItemDetail toItemDetail(ItemDetailDto itemDetailDto);

    // ✅ Convert ItemDetail → ItemDetailDto
    ItemDetailDto toItemDetailDto(ItemDetail itemDetail);

    // ✅ Convert List<ItemDetailDto> → List<ItemDetail>
    List<ItemDetail> toItemDetailList(List<ItemDetailDto> itemDetailDtoList);

    // ✅ Convert List<ItemDetail> → List<ItemDetailDto>
    List<ItemDetailDto> toItemDetailDtoList(List<ItemDetail> itemDetailList);

    // ✅ Update an existing ItemDetail from ItemDetailDto
    @Mapping(target = "itemId", ignore = true)
    void updateItemDetailFromDto(ItemDetailDto itemDetailDto, @MappingTarget ItemDetail itemDetail);

        @Mapping(target = "itemId", ignore = true) // Avoid modifying primary key
        @Mapping(source = "itemName", target = "name")
        void updateItemFromItemDetailDto(ItemDetailDto dto, @MappingTarget Item entity);

        void updateItemDetailFromItemDetailDto(ItemDetailDto itemDetailDto,@MappingTarget ItemDetail itemDetail);
    }


