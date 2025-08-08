package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  OrdersRepository extends JpaRepository<Orders,Integer> {
}
