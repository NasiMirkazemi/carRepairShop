package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.InvoiceDto;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.entity.Invoice;
import com.first.carRepairShop.entity.InvoiceStatus;
import com.first.carRepairShop.entity.Item;
import com.first.carRepairShop.entity.Services;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.BusinessConflictException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.InvoiceMapper;
import com.first.carRepairShop.mapper.ItemDetailMapper;
import com.first.carRepairShop.mapper.ServiceDetailMapper;
import com.first.carRepairShop.repository.InvoiceRepository;
import com.first.carRepairShop.repository.ItemRepository;
import com.first.carRepairShop.repository.ServicesRepository;
import com.first.carRepairShop.services.InvoiceLiner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceLineServiceImpl implements InvoiceLiner {
    private final InvoiceRepository invoiceRepository;
    private final ItemDetailMapper itemDetailMapper;
    private final ServiceDetailMapper serviceDetailMapper;
    private final ServicesRepository servicesRepository;
    private final InvoiceMapper invoiceMapper;
    private final ItemRepository itemRepository;


    public void updateItemsDetailListForInvoice(Integer invoiceId, List<ItemDetailDto> itemDetailDtoList) {
        if (invoiceId == null) throw new BadRequestException("Invoice Id is required");
        if (itemDetailDtoList == null || itemDetailDtoList.isEmpty()) {
            throw new BadRequestException("Item list is required");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("No Invoice found with id :" + invoiceId));
        if (!invoice.getInvoiceStatus().equals(InvoiceStatus.DRAFT)) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }

        List<ItemDetail> itemDetailList = invoice.getItemsDetailList();

        for (ItemDetailDto itemDetailDto : itemDetailDtoList) {
            itemDetailList.stream()
                    .filter(itemDetail -> itemDetail.getItemId().equals(itemDetailDto.getItemId())) // Match existing item
                    .findFirst()
                    .ifPresentOrElse(
                            itemDetail -> itemDetail.setItemPrice(itemDetailDto.getItemPrice()), // Update item price
                            () -> itemDetailList.add(itemDetailMapper.toItemDetail(itemDetailDto)) // Add new item if not found
                    );
        }
        invoice.setItemsDetailList(itemDetailList); // Set updated details back to Invoice
        invoiceRepository.save(invoice);

    }

    public void updateServiceDetailListForInvoice(Integer invoiceId, List<ServiceDetailDto> serviceDetailDtoList) {
        if (invoiceId == null) throw new BadRequestException("Invoice Id is required");
        if (serviceDetailDtoList == null || serviceDetailDtoList.isEmpty()) {
            throw new BadRequestException("serviceDto List is required");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("No Invoice found with id :" + invoiceId));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        List<ServiceDetail> serviceDetailList = invoice.getServicesDetailList();
        for (ServiceDetailDto serviceDetailDto : serviceDetailDtoList) {
            serviceDetailList.stream()
                    //filter() keeps only elements that satisfy the condition
                    .filter(serviceDetail -> serviceDetail.getServiceId().equals(serviceDetailDto.getServiceId()))
                    // Gets the first matching service (if it exists)
                    .findFirst()
                    .ifPresentOrElse(
                            serviceDetail -> serviceDetail.setServicePrice(serviceDetailDto.getServicePrice()), // if firstFind find match
                            () -> serviceDetailList.add(serviceDetailMapper.toServiceDetail(serviceDetailDto)));//if firstFind doesn't find match
        }
        invoice.setServicesDetailList(serviceDetailList);
        invoiceRepository.save(invoice);
    }

    public InvoiceDto addOrUpdateServiceDetailToInvoice(Integer invoiceId, ServiceDetailDto serviceDetailDto) {
        if (serviceDetailDto == null) {
            throw new IllegalArgumentException("serviceDetailDto cannot be null");
        }
        if (serviceDetailDto.getServiceId() == null) {
            throw new BadRequestException("Service ID in serviceDetailDto cannot be null");
        }
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with id: " + invoiceId + " not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        Services services = servicesRepository.findById(serviceDetailDto.getServiceId())
                .orElseThrow(() -> new NotFoundException("No Service found with id : " + serviceDetailDto.getServiceId()));

        List<ServiceDetail> serviceDetailList = invoice.getServicesDetailList();
        serviceDetailList.stream()
                // returns a new stream containing only the elements that satisfy the given condition.
                .filter(serviceDetail -> serviceDetail.getServiceId().equals(serviceDetailDto.getServiceId()))
                .findFirst()
                .ifPresentOrElse(serviceDetail -> serviceDetailMapper.updateServiceDetailFromDto(serviceDetailDto, serviceDetail)
                        , () -> {
                            ServiceDetail newServiceDetail = serviceDetailMapper.toServiceDetail(serviceDetailDto);
                            newServiceDetail.setServiceId(services.getServiceId());
                            serviceDetailList.add(newServiceDetail);
                        });
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    public InvoiceDto removeServiceDetailFromInvoice(Integer invoiceId, ServiceDetailDto serviceDetailDto) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        if (serviceDetailDto == null) {
            throw new IllegalArgumentException("serviceDetailDto cannot be null");
        }
        if (serviceDetailDto.getServiceId() == null) {
            throw new BadRequestException("Service ID in serviceDetailDto cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("invoice whit id : " + invoiceId + "not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }

        boolean removed = invoice.getServicesDetailList()
                //Iterates over invoice.getServicesDetailSet().
                .removeIf(serviceDetail -> serviceDetail.getServiceId().equals(serviceDetailDto.getServiceId()));
        if (!removed) {
            throw new NotFoundException("ServiceDetail with this serviceId does not exist: " + serviceDetailDto.getServiceId());
        }
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    public ServiceDetailDto getServiceDetailFromInvoice(Integer invoiceId, Integer serviceId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        if (serviceId == null) {
            throw new IllegalArgumentException("serviceId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with id: " + invoiceId + " not found"));
        ServiceDetail existServiceDetail = invoice.getServicesDetailList().stream()
                .filter(serviceDetail -> serviceDetail.getServiceId().equals(serviceId))
                .findAny()
                .orElseThrow(() -> new NotFoundException("ServiceDetail with service id: " + serviceId + " not found"));
        return serviceDetailMapper.toServiceDetailDto(existServiceDetail);
    }


    public List<ServiceDetailDto> getAllServiceDetailFromInvoices(Integer invoiceId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with id: " + invoiceId + " not found"));
        return serviceDetailMapper.toServiceDetailDtoListFromServiceDetail(invoice.getServicesDetailList());
    }

    public InvoiceDto clearAllServiceDetailFromInvoice(Integer invoiceId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with id: " + invoiceId + " not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        if (invoice.getServicesDetailList().isEmpty()) {
            throw new BusinessConflictException("No service details to clear for this invoice.");
        }

        invoice.getServicesDetailList().clear();
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    public InvoiceDto addOrUpdateItemDetailToInvoice(Integer invoiceId, ItemDetailDto itemDetailDto) {
        if (itemDetailDto == null) {
            throw new IllegalArgumentException("item Detail cannot be null");
        }
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        if (itemDetailDto.getItemId() == null) {
            throw new BadRequestException("Item Id cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("invoice whit id :" + invoiceId + "not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        Item item = itemRepository.findById(itemDetailDto.getItemId())
                .orElseThrow(() -> new NotFoundException("No item found with id: " + itemDetailDto.getItemId()));
        invoice.getItemsDetailList().stream()
                .filter(itemDetail -> itemDetail.getItemId().equals(itemDetailDto.getItemId()))
                .findFirst()
                .ifPresentOrElse(itemDetail -> itemDetailMapper.updateItemDetailFromDto(itemDetailDto, itemDetail),
                        () -> {
                            ItemDetail newItemDetail = itemDetailMapper.toItemDetail(itemDetailDto);
                            newItemDetail.setItemId(item.getItemId());
                            invoice.getItemsDetailList().add(newItemDetail);
                        });
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    public InvoiceDto removeItemDetailFromInvoice(Integer invoiceId, ItemDetailDto itemDetailDto) {
        if (itemDetailDto == null) {
            throw new IllegalArgumentException("ItemDetail cannot be null");
        }
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        if (itemDetailDto.getItemId() == null) {
            throw new BadRequestException("Item id in itemDetailDto cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("invoice with this id: " + invoiceId + "not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        boolean removed = invoice.getItemsDetailList()
                .removeIf(itemDetail -> itemDetail.getItemId().equals(itemDetailDto.getItemId()));
        if (!removed) {
            throw new NotFoundException("itemDetail whit this itemId:" + itemDetailDto.getItemId() + " is not");
        }
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    public ItemDetailDto getItemDetailFromInvoice(Integer invoiceId, Integer itemId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        if (itemId == null) {
            throw new IllegalArgumentException("itemId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("invoice whit id: " + invoiceId + " not found"));
        ItemDetail existItemDetail = invoice.getItemsDetailList().stream()
                .filter(itemDetail -> itemId.equals(itemDetail.getItemId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("itemDetail with item id: " + itemId + " not found"));
        return itemDetailMapper.toItemDetailDto(existItemDetail);
    }

    public List<ItemDetailDto> getAllItemDetailFromInvoices(Integer invoiceId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoiceId cannot be null");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("invoice with id: " + invoiceId + " not found"));
        return itemDetailMapper.toItemDetailDtoList(invoice.getItemsDetailList());
    }

    public InvoiceDto clearAllItemDetailFromInvoice(Integer invoiceId) {
        if (invoiceId == null)
            throw new IllegalArgumentException("invoice Id cannot be null");
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + invoiceId + " not found"));
        if (!InvoiceStatus.DRAFT.equals(invoice.getInvoiceStatus())) {
            throw new BusinessConflictException("Only draft invoices can be modified.");
        }
        invoice.getItemsDetailList().clear();
        invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }


}
