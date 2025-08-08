package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Item;
import com.first.carRepairShop.entity.ItemQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    Item findByName(String name);
    Item findByType(String type);

}
