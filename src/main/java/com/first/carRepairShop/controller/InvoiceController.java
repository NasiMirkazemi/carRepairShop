package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.CreateInvoiceDto;
import com.first.carRepairShop.dto.CreditNoteDto;
import com.first.carRepairShop.dto.InvoiceDto;
import com.first.carRepairShop.services.InvoiceService;
import com.first.carRepairShop.services.impl.InvoiceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/invoice")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody CreateInvoiceDto createInvoiceDto) {
        InvoiceDto invoiceDto = invoiceService.createInvoice(createInvoiceDto);
        return ResponseEntity.ok(invoiceDto);
    }

    @PutMapping("/invoices/{invoiceId}/finalize")
    public ResponseEntity<InvoiceDto> finalizeInvoice(
            @PathVariable("invoiceId") Integer invoiceId,
            @RequestParam("finalizedBy") String finalizedBy) {
        InvoiceDto invoiceDto = invoiceService.finalizeInvoice(invoiceId, finalizedBy);
        return ResponseEntity.ok(invoiceDto);
    }

    @GetMapping("/getByInvoiceNumber/{invoiceNumber}")
    public ResponseEntity<InvoiceDto> getInvoiceByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber) {
        InvoiceDto invoiceDto = invoiceService.getInvoiceByInvoiceNumber(invoiceNumber);
        return ResponseEntity.ok(invoiceDto);
    }

    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<String> deleteInvoice(@PathVariable("invoiceId") Integer invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.ok("Invoice has been deleted with id: " + invoiceId);
    }

    @DeleteMapping("/deleteByNumber/{invoiceNumber}")
    public ResponseEntity<String> deleteInvoiceByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber) {
        invoiceService.deleteInvoiceByInvoiceNumber(invoiceNumber);
        return ResponseEntity.ok("Invoice has been deleted with id: " + invoiceNumber);
    }

    @PutMapping("/cancel/{invoiceId}")
    public ResponseEntity<String> cancelInvoice(@PathVariable("invoiceId") Integer invoiceId) {
        invoiceService.cancelInvoice(invoiceId);
        return ResponseEntity.ok("Invoice has been canceled with id: " + invoiceId);
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<InvoiceDto> findInvoiceWithCustomerId(@PathVariable("customerId") Integer customerId) {
        InvoiceDto invoiceDto = invoiceService.findInvoiceWithCustomerId(customerId);
        return ResponseEntity.ok(invoiceDto);
    }

    @PostMapping("/createCreditNote")
    public ResponseEntity<CreditNoteDto> createCreditNote(@RequestBody CreditNoteDto creditNoteDto) {
        CreditNoteDto creditNoteDto1 = invoiceService.createCreditNote(creditNoteDto);
        return ResponseEntity.ok(creditNoteDto1);
    }

    public ResponseEntity<String> markAsPaid(@PathVariable("invoiceId") Integer invoiceId, String paymentInfo) {
        invoiceService.markAsPaid(invoiceId, paymentInfo);
        return ResponseEntity.ok("");
    }


}
