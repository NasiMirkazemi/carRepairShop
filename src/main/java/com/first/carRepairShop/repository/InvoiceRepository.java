package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    int deleteByInvoiceNumber(String invoiceNumber);


    @Query("select i from Invoice i where i.customer.id=:customerId ")
    Optional<Invoice> findByCustomerId(@Param("customerId") Integer customerId);


    @Transactional
    @Modifying
    @Query("delete  from Invoice i where i.customer.id=:customerId ")
    int deleteByCustomerId(@Param("customerId") Integer customerId);

    boolean existsByRepairOrder_RepairOrderId(Integer repairOrderId);

    Optional<Invoice> findInvoiceByRepairOrder_RepairOrderId(Integer repairOrderId);

}

