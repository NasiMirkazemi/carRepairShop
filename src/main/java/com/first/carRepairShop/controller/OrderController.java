package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.OrdersDto;
import com.first.carRepairShop.services.OrderService;
import com.first.carRepairShop.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<Page<OrdersDto>> getOrders( Pageable pageable) {
        Page<OrdersDto> page = orderService.listOrders(pageable);
        return ResponseEntity.ok(page);

    }

}
