package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.OrderItemDto;
import com.first.carRepairShop.dto.OrdersDto;
import com.first.carRepairShop.dto.ReceivedOrderRequest;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.OrderItemMapper;
import com.first.carRepairShop.mapper.OrdersMapper;
import com.first.carRepairShop.repository.EmployeeRepository;
import com.first.carRepairShop.repository.ItemRepository;
import com.first.carRepairShop.repository.OrderItemRepository;
import com.first.carRepairShop.repository.OrdersRepository;
import com.first.carRepairShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final EmployeeRepository employeeRepository;
    private final OrderItemMapper orderItemMapper;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;


    public OrdersDto createOrder(OrdersDto ordersDto) {
        if (ordersDto == null) throw new BadRequestException("Order Info is required");
        if (ordersDto.getCreatedBy() == null) throw new BadRequestException("Create by is required");
        Employee employee = employeeRepository.findById(ordersDto.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("No Employee found with id: " + ordersDto.getCreatedBy()));

        Orders order = Orders.builder()
                .orderNumber(generateOrderNumber(LocalDate.now()))
                .supplier(ordersDto.getSupplier())
                .createdBy(employee)
                .build();
        List<OrderItem> orderItems = orderItemMapper.toOrderItemList(ordersDto.getOrderItems());
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrders(order);
        }
        order.setOrderItems(orderItems);
        Orders savedOrders = ordersRepository.save(order);
        return ordersMapper.toDto(savedOrders);

    }

    @Override
    @Transactional
    public OrderItemDto addOrderItem(Integer orderId, OrderItemDto orderItemDto) {
        if (orderId == null) throw new BadRequestException("Order id is required");
        if (orderItemDto == null) throw new BadRequestException("Order Item info is required");
        if (orderItemDto.getItemId() == null) throw new BadRequestException("Item id is required");
        if (orderItemDto.getQuantity() <= 0) throw new BadRequestException("Quantity must be greater than zero");

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No Order found with id: " + orderId));
        Item item = itemRepository.findById(orderItemDto.getItemId())
                .orElseThrow(() -> new NotFoundException("No Item found with id: " + orderItemDto.getItemId()));
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        orderItem.setItem(item);
        orderItem.setOrders(order);
        order.getOrderItems().add(orderItem);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDto(savedOrderItem);

    }

    @Override
    @Transactional
    public void removeOrderItem(Integer orderId, Integer orderItemId) {
        if (orderId == null) throw new BadRequestException("Order id is required");
        if (orderItemId == null) throw new BadRequestException("Order Item id is required");
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No Order found with id: " + orderId));
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("No Order Item found with id :" + orderItemId));
        orderItem.setOrders(null);
        order.getOrderItems().remove(orderItem);
        log.info("Order item with id {} from order with id {} has been deleted", orderItem.getOrderItemId(), order.getOrderId());

    }

    @Override
    @Transactional
    public OrdersDto updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        if (orderId == null) throw new BadRequestException("Order id is required");
        if (newStatus == null) throw new BadRequestException("New Status is required");
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No Order found with id: " + orderId));
        order.setStatus(newStatus);
        return ordersMapper.toDto(order);

    }

    @Override
    public OrdersDto getOrderById(Integer orderId) {
        if (orderId == null) throw new BadRequestException("Order id is required");
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No order found with id: " + orderId));
        return ordersMapper.toDto(order);
    }

    @Override
    public Page<OrdersDto> listOrders(Pageable pageable) {
        Page<Orders> ordersPage = ordersRepository.findAll(pageable);
        return ordersPage.map(ordersMapper::toDto);
    }
    /*Pageable is a feature in Spring Data JPA that lets you split large database query results into pages,
     so you don’t fetch thousands of rows at once. Instead, you can request only part of the data — for example,
     “give me page 2 with 10 orders per page.” It automatically handles limit/offset in the SQL behind the scenes.

This is very useful for lists that can grow big, like Orders,Users,
or Products. Instead of findAll(), you use findAll(Pageable) and Spring returns a Page<T> which has:
    •	the list of items for that page
    •	total number of elements
    •	total number of pages
    •	current page info
It’s directly connected to controller endpoints that return list results
— so clients (frontend or API consumer) can request ?page=0&size=10&sort=orderDate,desc to get exactly the slice they want.
⸻
*/

    @Override
    public void deleteOrder(Integer orderId) {
        if (orderId == null) throw new BadRequestException("Order id is required");
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No Order found with id: " + orderId));
        ordersRepository.delete(order);
        log.info("Order with id {} has been deleted", order.getOrderId());

    }

    @Override
    public OrdersDto receiveOrder(Integer orderId, ReceivedOrderRequest receivedOrderRequest) {
        if (orderId == null) throw new BadRequestException("Order id is required ");
        if (receivedOrderRequest == null) throw new BadRequestException("Received Order info is required");
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No Order found with id: " + orderId));
        Employee employee = employeeRepository.findById(receivedOrderRequest.getReceivedBy())
                .orElseThrow(() ->
                        new NotFoundException("No Employee for ReceivedBy found with id: " + receivedOrderRequest.getReceivedBy()));

        order.setReceivedBy(employee);
        order.setReceivingComment(receivedOrderRequest.getComment());
        order.setStatus(OrderStatus.RECEIVED);
        order.setReceivedDate(LocalDateTime.now());
        Orders updatedOrder = ordersRepository.save(order);
        return ordersMapper.toDto(updatedOrder);
    }

    private String generateOrderNumber(LocalDate localDate) {
        String date = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String redisKey = "order_seq:" + date;
        Long sequence = redisTemplate.opsForValue().increment(redisKey);
        if (sequence == 1) {
            redisTemplate.expire(redisKey, Duration.ofDays(1));
        }
        return String.format("ORD-%s-%04d", date, sequence);
    }

    public BigDecimal getTotalPrice(OrderItemDto orderItemDto) {
        if (orderItemDto == null) throw new BadRequestException("Order Item info is required");
        Item item = itemRepository.findById(orderItemDto.getItemId())
                .orElseThrow(() -> new NotFoundException("No Item found with id: " + orderItemDto.getItemId()));
        return item.getItemPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity()));
    }


}
