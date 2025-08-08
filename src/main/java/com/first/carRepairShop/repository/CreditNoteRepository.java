package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.CreditNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditNoteRepository extends JpaRepository<CreditNote, Integer> {
    boolean existsByCreditNoteNumber(String creditNoteNumber);
}
