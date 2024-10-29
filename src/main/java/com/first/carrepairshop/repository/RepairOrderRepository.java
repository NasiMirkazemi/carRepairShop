package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder,Integer> {
}
