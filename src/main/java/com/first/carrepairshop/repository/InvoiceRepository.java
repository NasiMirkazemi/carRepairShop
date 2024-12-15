package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findByInvoiceNumber(Integer invoiceNumber);

    void deleteByInvoiceNumber(Integer invoiceNumber);

    @Query("select i from Invoice i where i.customer.id=:id ")
    Invoice findByCustomer(@Param("id") Integer id);


    @Transactional
    @Modifying
    @Query("delete  from Invoice i where i.customer.id=:id ")
    void deleteByCustomerId(@Param("id") Integer id);


}

