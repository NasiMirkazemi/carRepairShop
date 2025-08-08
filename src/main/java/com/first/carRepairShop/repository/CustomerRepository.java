package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   Optional<Customer>  findByPhone(String phone);

    List<Customer> findCustomerByNameAndLastname(String name, String lastname);

    @Query("select c from Customer c where c.customerNumber=:customerNumber")
    Optional<Customer> findByCustomerNumber(@Param("customerNumber") String customerNumber);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.name=:name and c.lastname=:lastname")
    void deleteByNameAndLastname(@Param("name") String name, @Param("lastname") String lastname);

    @Transactional
    @Modifying
    @Query("update Customer c set c.email=:email where c.id=:id")
    Customer updateByEmail(@Param("id") Integer id, @Param("email") String email);

 /*   @Query("select c from Customer c where c.name=:name and c.lastname=:lastname")
    Optional<Customer> findCustomerByNameAndLastname(@Param("name") String name, @Param("lastname") String lastname);
*/
    @Transactional
    @Modifying
    @Query("update Customer c set c.phone=:phone where c.id=:id")
    Customer updateByPhone(@Param("id") Integer id, @Param("phone") String phone);




}

