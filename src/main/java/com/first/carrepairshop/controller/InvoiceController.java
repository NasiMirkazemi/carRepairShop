package com.first.carrepairshop.controller;

import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoiceController")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/add")
    public ResponseEntity<InvoiceDto> add(InvoiceDto invoiceDto) {
        return ResponseEntity.ok(invoiceService.addInvoice(invoiceDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InvoiceDto> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(invoiceService.getInvoice(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<InvoiceDto> update(@RequestBody InvoiceDto invoiceDto) {
        return ResponseEntity.ok(invoiceService.update(invoiceDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("invoice by id :" + id + "is deleted");
    }


}
