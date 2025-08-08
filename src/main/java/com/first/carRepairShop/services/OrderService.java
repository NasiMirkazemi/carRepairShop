package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.OrderItemDto;
import com.first.carRepairShop.dto.OrdersDto;
import com.first.carRepairShop.dto.ReceivedOrderRequest;
import com.first.carRepairShop.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrdersDto createOrder(OrdersDto ordersDto);

    OrderItemDto addOrderItem(Integer orderId, OrderItemDto orderItemDto);

    void removeOrderItem(Integer orderId, Integer orderItemId);

    OrdersDto updateOrderStatus(Integer orderId, OrderStatus newStatus);

    OrdersDto getOrderById(Integer orderId);

    Page<OrdersDto> listOrders(Pageable pageable);

    void deleteOrder(Integer orderId);

    OrdersDto receiveOrder(Integer orderId, ReceivedOrderRequest receivedOrderRequest);

}
