package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.CreateInvoiceDto;
import com.first.carRepairShop.dto.CreditNoteDto;
import com.first.carRepairShop.dto.InvoiceDto;

import java.math.BigDecimal;

public interface InvoiceService {
    InvoiceDto createInvoice(CreateInvoiceDto createInvoiceDto);

    InvoiceDto finalizeInvoice(Integer invoiceId, String finalizedBy);
    InvoiceDto getInvoice(Integer id);

    InvoiceDto getInvoiceByInvoiceNumber(String invoiceNumber);

    void deleteInvoice(Integer id);

    void deleteInvoiceByInvoiceNumber(String invoiceNumber);

    void cancelInvoice(Integer invoiceId);

    InvoiceDto findInvoiceWithCustomerId(Integer customerId);

    CreditNoteDto createCreditNote(CreditNoteDto creditNoteDto);

    void markAsPaid(Integer invoiceId, String paymentInfo);
}
