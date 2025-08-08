package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, Integer> {
    List<InventoryLog> findAllByInventory_InventoryId(Integer id);
    List<InventoryLog> findAll();
}
