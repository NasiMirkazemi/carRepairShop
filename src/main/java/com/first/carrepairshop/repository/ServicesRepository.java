package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository

public interface ServicesRepository extends JpaRepository<Services, Integer> {
    Services findByServiceName(String serviceName);

    //  Services findByMechanics(Mechanic mechanic);
    void deleteByServiceName(String serviceName);

    @Transactional
    @Modifying
    @Query("update Services service set service.price=:price where service.serviceId=:serviceId ")
    Integer updateByPrice(@Param("serviceId") Integer serviceId, @Param("price") Integer price);

    @Transactional
    @Modifying
    @Query(" update Services service set service.scheduledTime=:scheduledTime where service.serviceId=:serviceId")
    Integer updateByScheduledTime(@Param("serviceId") Integer serviceId, @Param("scheduledTime") String scheduledTime);


}

