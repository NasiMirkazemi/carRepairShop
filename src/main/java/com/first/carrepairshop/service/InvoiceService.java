package com.first.carrepairshop.service;

import com.first.carrepairshop.associations.ItemDetail;
import com.first.carrepairshop.dto.InvoiceDto;
import com.first.carrepairshop.entity.Invoice;
import com.first.carrepairshop.entity.Item;
import com.first.carrepairshop.exception.NotfoundException;
import com.first.carrepairshop.mapper.CustomerMapper;
import com.first.carrepairshop.mapper.InvoiceMapper;
import com.first.carrepairshop.mapper.ItemDetailMapper;
import com.first.carrepairshop.mapper.RepairOrderMapper;
import com.first.carrepairshop.repository.InvoiceRepository;
import com.first.carrepairshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerMapper customerMapper;
    private final InvoiceMapper invoiceMapper;
    private final ItemRepository itemRepository;
    private final ItemDetailMapper itemDetailMapper;
    private final RepairOrderMapper repairOrderMapper;


    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoiceEntity = invoiceMapper.toInvoiceEntity(invoiceDto);
        Invoice savedInvoice = invoiceRepository.save(invoiceEntity);
        return invoiceMapper.toInvoiceDto(savedInvoice);
    }

    public InvoiceDto getInvoice(Integer id) {
        Invoice invoiceEntity = invoiceRepository.findById(id).get();
        return invoiceMapper.toInvoiceDto(invoiceEntity);
    }

    public InvoiceDto update(InvoiceDto invoiceDto) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceDto.getInvoiceId());
        Invoice invoiceEntity = null;
        if (invoiceOptional.isPresent()) {
            invoiceEntity = invoiceOptional.get();
            if (invoiceDto.getInvoiceNumber() != null)
                invoiceEntity.setInvoiceNumber(invoiceDto.getInvoiceNumber());
            if (invoiceDto.getTotalAmount() != null)
                invoiceEntity.setTotalAmount(invoiceDto.getTotalAmount());
            if (invoiceDto.getCarId() != null)
                invoiceEntity.setCarId(invoiceDto.getCarId());
            if (invoiceDto.getCustomerDto() != null)
                invoiceEntity.setCustomer(customerMapper.toCustomerEntity(invoiceDto.getCustomerDto()));
            if (invoiceDto.getRepairOrderDto() != null)
                invoiceEntity.setRepairOrder(repairOrderMapper.toRepairOrderEntity(invoiceDto.getRepairOrderDto()));
            if (invoiceDto.getItemsDetailList() != null && !invoiceDto.getItemsDetailList().isEmpty()) {
                List<Integer> itemDetailId = invoiceEntity.getItemsDetailList().stream().map(ItemDetail::getItemId).toList();
                List<ItemDetail> itemDetailRemove = invoiceEntity.getItemsDetailList()
                        .stream()
                        .filter(itemDetail -> invoiceDto.getItemsDetailList().stream().noneMatch(itemDDto -> itemDDto.getItemId().equals(itemDetail.getItemId())))
                        .toList();
                invoiceEntity.getItemsDetailList().removeAll(itemDetailRemove);
                List<ItemDetail> listToAddOrUpdate = invoiceDto.getItemsDetailList().stream()
                        .map(itemDetailMapper::toItemDetailEntity)
                        .toList();
                for (ItemDetail itemDetail : listToAddOrUpdate) {
                    Item item = itemRepository.findById(itemDetail.getItemId())
                            .orElseThrow(() -> new NotfoundException("item not found"));
                    itemDetail.setItemName(item.getName());
                    itemDetail.setItemPrice(item.getPrice());
                }


            }

            invoiceRepository.save(invoiceEntity);
        }
        return invoiceMapper.toInvoiceDto(invoiceEntity);

    }

    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
        System.out.println("invoice" + id + "deleted");
    }


}
