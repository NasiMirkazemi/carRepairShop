package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.OrderItemDto;
import com.first.carRepairShop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "orderId", source = "orders.orderId")
    @Mapping(target = "itemId", source = "item.itemId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "orders.orderId", source = "orderId")
    @Mapping(target = "item.itemId", source = "itemId")
    OrderItem toEntity(OrderItemDto orderItemDto);



    List<OrderItemDto> toOrderDtoList(List<OrderItem> orderItemList);

    List<OrderItem> toOrderItemList(List<OrderItemDto> orderItemDtoList);


}
