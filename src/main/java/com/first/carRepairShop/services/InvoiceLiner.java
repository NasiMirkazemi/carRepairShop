package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.InvoiceDto;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;

import java.util.List;

public interface InvoiceLiner {
    void updateItemsDetailListForInvoice(Integer invoiceId, List<ItemDetailDto> itemDetailDtoList);

    void updateServiceDetailListForInvoice(Integer invoiceId, List<ServiceDetailDto> serviceDetailDtoList);

    InvoiceDto addOrUpdateServiceDetailToInvoice(Integer invoiceId, ServiceDetailDto serviceDetailDto);

    InvoiceDto removeServiceDetailFromInvoice(Integer invoiceId, ServiceDetailDto serviceDetailDto);

    ServiceDetailDto getServiceDetailFromInvoice(Integer invoiceId, Integer serviceId);

    List<ServiceDetailDto> getAllServiceDetailFromInvoices(Integer invoiceId);

    InvoiceDto clearAllServiceDetailFromInvoice(Integer invoiceId);

    InvoiceDto addOrUpdateItemDetailToInvoice(Integer invoiceId, ItemDetailDto itemDetailDto);

    InvoiceDto removeItemDetailFromInvoice(Integer invoiceId, ItemDetailDto itemDetailDto);

    ItemDetailDto getItemDetailFromInvoice(Integer invoiceId, Integer itemId);

    List<ItemDetailDto> getAllItemDetailFromInvoices(Integer invoiceId);
}
