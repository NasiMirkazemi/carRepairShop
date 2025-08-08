package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByNumberPlate(String numberPlate);

    List<Car> findByModel(String model);

    List<Car> findByMake(String make);

    List<Car> findByYear(Integer year);

    void deleteCarByCarId(Integer carId);

    void deleteByNumberPlate(String numberPlate);
    boolean existsByNumberPlate(String numberPlate);

    @Query(" SELECT c FROM  Car c WHERE  c.customer.name=:customerName AND  c.customer.lastname=:customerLastname   ")
    List<Car> findByCustomerName(@Param("customerName") String customerName, @Param("customerLastname") String customerLastname);

    @Transactional
    @Query("DELETE  FROM  Car c WHERE  c.customer.id=:customerId")
    @Modifying
    void deleteByCustomer_CustomerId(@Param("customerId") Integer customerId);

    @Transactional
    @Query("DELETE FROM  Car c WHERE  c.customer.name=:customerName AND  c.customer.lastname=:customerLastname")
    @Modifying
    void deleteByCustomerNameAndCustomerLastname(@Param("customerName") String customerName, @Param("customerLastname") String customerLastname);

}


