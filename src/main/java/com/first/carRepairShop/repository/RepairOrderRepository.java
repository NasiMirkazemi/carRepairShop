package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Integer> {
    /* @Query("select r from RepairOrder r where r.car.carId =: carId")
     List<RepairOrder> findAllByCarId(@Param("carId") Integer carId);
 */
    List<RepairOrder> findAllByCar_CarId( Integer carId);
}
