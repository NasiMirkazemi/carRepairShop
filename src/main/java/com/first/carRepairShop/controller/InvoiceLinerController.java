package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.InvoiceDto;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.services.InvoiceLiner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoiceLiner")
public class InvoiceLinerController {
    private final InvoiceLiner invoiceLiner;

    @PutMapping("/updateItems/{invoiceId}")
    ResponseEntity<Void> updateItemsDetailListForInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                         @RequestBody List<ItemDetailDto> itemDetailDtoList) {
        invoiceLiner.updateItemsDetailListForInvoice(invoiceId, itemDetailDtoList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateService/{invoiceId}")
    ResponseEntity<Void> UpdateServiceDetailListForInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                           @RequestBody List<ServiceDetailDto> serviceDetailDtoList) {
        invoiceLiner.updateServiceDetailListForInvoice(invoiceId, serviceDetailDtoList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/addOrUpdateService/{invoiceId}")
    ResponseEntity<InvoiceDto> addOrUpdateServiceDetailToInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                                 @RequestBody ServiceDetailDto serviceDetailDto) {
        InvoiceDto invoiceDto = invoiceLiner.addOrUpdateServiceDetailToInvoice(invoiceId, serviceDetailDto);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @DeleteMapping ("/removeService/{invoiceId}")
    ResponseEntity<InvoiceDto> removeServiceDetailFromInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                              @RequestBody ServiceDetailDto serviceDetailDto) {
        InvoiceDto invoiceDto = invoiceLiner.removeServiceDetailFromInvoice(invoiceId, serviceDetailDto);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @GetMapping("/getService/invoice/{invoiceId}/service/{serviceId}")
    ResponseEntity<ServiceDetailDto> getServiceDetailFromInvoice(@PathVariable("invoiceId") Integer invoiceId
            , @PathVariable("serviceId") Integer serviceId) {
        ServiceDetailDto serviceDetailDto = invoiceLiner.getServiceDetailFromInvoice(invoiceId, serviceId);
        return ResponseEntity.ok().body(serviceDetailDto);
    }

    @GetMapping("/getAllService/{invoiceId}")
    ResponseEntity<List<ServiceDetailDto>> getAllServiceDetailFromInvoices(@PathVariable("invoiceId") Integer invoiceId) {
        List<ServiceDetailDto> serviceDetailDtoList = invoiceLiner.getAllServiceDetailFromInvoices(invoiceId);
        return ResponseEntity.ok().body(serviceDetailDtoList);
    }

    @DeleteMapping("/deleteAllService/{invoiceId}")
    ResponseEntity<InvoiceDto> clearAllServiceDetailFromInvoice(@PathVariable("invoiceId") Integer invoiceId) {
        InvoiceDto invoiceDto = invoiceLiner.clearAllServiceDetailFromInvoice(invoiceId);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @PutMapping("/addOrUpdateItem/{invoiceId}")
    ResponseEntity<InvoiceDto> addOrUpdateItemDetailToInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                              @RequestBody ItemDetailDto itemDetailDto) {
        InvoiceDto invoiceDto = invoiceLiner.addOrUpdateItemDetailToInvoice(invoiceId, itemDetailDto);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @DeleteMapping("/removeItem/{invoiceId}")
    ResponseEntity<InvoiceDto> removeItemDetailFromInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                           @RequestBody ItemDetailDto itemDetailDto) {
        InvoiceDto invoiceDto = invoiceLiner.removeItemDetailFromInvoice(invoiceId, itemDetailDto);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @GetMapping("/getItem/invoice/{invoiceId}/item/{itemId}")
    ResponseEntity<ItemDetailDto> getItemDetailFromInvoice(@PathVariable("invoiceId") Integer invoiceId,
                                                           @PathVariable("itemId") Integer itemId) {
        ItemDetailDto itemDetailDto = invoiceLiner.getItemDetailFromInvoice(invoiceId, itemId);
        return ResponseEntity.ok().body(itemDetailDto);
    }

    @GetMapping("/getAllItem/{invoiceId}")
    ResponseEntity<List<ItemDetailDto>> getAllItemDetailFromInvoices(@PathVariable("invoiceId") Integer invoiceId) {
        List<ItemDetailDto> itemDetailDtoList = invoiceLiner.getAllItemDetailFromInvoices(invoiceId);
        return ResponseEntity.ok().body(itemDetailDtoList);

    }


}
