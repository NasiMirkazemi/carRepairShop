package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.name=:name and c.lastname=:lastname")
    void deleteByNameAndLastname(@Param("name") String name, @Param("lastname") String lastname);

    @Transactional
    @Modifying
    @Query("update Customer c set c.email=:email where c.customerId=:customerId")
    Customer updateByEmail(@Param("customerId") Integer customerId, @Param("email") String email);

    @Query("select c from Customer c where c.name=:name and c.lastname=:lastname")
    Customer getCustomerByNameAndLastname(@Param("name") String name, @Param("lastname") String lastname);


    @Transactional
    @Modifying
    @Query("update Customer c set c.phone=:phone where c.customerId=:customerId")
    Customer updateByPhone(@Param("customerId") Integer customerId, @Param("phone") String phone);
}

