package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface
InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findInventoryByItem(Integer itemId);

    @Query("select i from Inventory i where i.quantity < i.minimumStockLevel ")
    Optional<List<Inventory>> findItemBlowMinimumStock();

    @Query("select i from Inventory i where i.quantity> i.minimumStockLevel")
    Optional<List<Inventory>> findItemsAboveMaximumStock();

    Optional<List<Inventory>> findInventoryByStorageLocation(String location);

    Optional<List<Inventory>> findInventoryBySupplier(String supplier);

}