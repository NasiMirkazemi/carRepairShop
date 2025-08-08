package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.InvoiceData;

public interface InvoicePreparationService {
    InvoiceData prepareInvoiceData(Integer repairOrderId);
}
