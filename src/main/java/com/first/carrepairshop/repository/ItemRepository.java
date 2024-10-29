package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Item;
import com.first.carrepairshop.entity.ItemQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    Item findByName(String name);
    Item findByType(String type);
    @Query("select i from Item i where i.price=:price and i.qualityLevel=:quality")
    List<Item>findAllByPriceAndQualityLevel(@Param("price") Integer price ,@Param("quality") ItemQuality quality);

}
