package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.OrdersDto;
import com.first.carRepairShop.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);

    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "receivedBy", source = "receivedBy.id")
    OrdersDto toDto(Orders orders);

    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "createdBy.id", source = "createdBy")
    @Mapping(target = "receivedBy.id", source = "receivedBy")
    Orders toEntity(OrdersDto ordersDto);

    List<OrdersDto> toOrdersDtoList(List<Orders> ordersList);

    List<Orders> toOrdersList(List<OrdersDto> ordersDtoList);
}
