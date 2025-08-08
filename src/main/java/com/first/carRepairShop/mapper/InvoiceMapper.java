package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.InvoiceDto;
import com.first.carRepairShop.entity.Invoice;
import com.first.carRepairShop.entity.InvoiceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Named("enumToString")
    static String mapInvoiceStatusToString(InvoiceStatus invoiceStatus) {
        return invoiceStatus != null ? invoiceStatus.name() : null;
    }

    @Named("stringToEnum")
    static InvoiceStatus mapStringToStatus(String status) {
        return status != null ? InvoiceStatus.valueOf(status.toUpperCase()) : null;
    }

    // 🔹 Entity → DTO (Basic field mappings)
    @Mapping(target = "repairOrderId", source = "repairOrder.repairOrderId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "carNumberPlate", source = "numberPlate")
    @Mapping(target = "servicesDetailDtoList", source = "servicesDetailList")
    @Mapping(target = "itemDetailDtoList", source = "itemsDetailList")
    @Mapping(target = "customerId", source = "customer.id")
    InvoiceDto toDto(Invoice invoice);

    // 🔹 DTO → Entity (Does NOT set `repairOrder`, handled in service layer)
    @Mapping(target = "servicesDetailList", source = "servicesDetailDtoList")
    @Mapping(target = "itemsDetailList", source = "itemDetailDtoList")
    @Mapping(target = "customer.id", source = "customerId")
    Invoice toEntity(InvoiceDto invoiceDto);
}