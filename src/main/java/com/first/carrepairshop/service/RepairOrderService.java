package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.RepairOrderDto;
import com.first.carrepairshop.entity.RepairOrder;
import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.repository.RepairOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairOrderService {
    private final RepairOrderRepository repairOrderRepository;

    public RepairOrderDto addRepairOrder(RepairOrderDto repairOrderDto) {
        RepairOrder repairOrder = repairOrderRepository.save(RepairOrder.builder()
                .description(repairOrderDto.getDescription())
                .serviceDate(repairOrderDto.getServiceDate())
                .customerId(repairOrderDto.getCustomerId())
                .mechanicId(repairOrderDto.getMechanicId())
                .items(repairOrderDto.getItems())
                .services(repairOrderDto.getServices())
                .build());
        return RepairOrderDto.builder()
                .repairOrderId(repairOrder.getRepairOrderId())
                .description(repairOrder.getDescription())
                .serviceDate(repairOrder.getServiceDate())
                .customerId(repairOrder.getCustomerId())
                .mechanicId(repairOrder.getMechanicId())
                .items(repairOrder.getItems())
                .services(repairOrder.getServices())
                .build();
    }
    public RepairOrderDto getRepairOrder(Integer id){
        RepairOrder repairOrderEntity=repairOrderRepository.findById(id).get();
         return RepairOrderDto.builder()
                 .repairOrderId(repairOrderEntity.getRepairOrderId())
                 .description(repairOrderEntity.getDescription())
                 .serviceDate(repairOrderEntity.getServiceDate())
                 .customerId(repairOrderEntity.getCustomerId())
                 .mechanicId(repairOrderEntity.getMechanicId())
                 .services(repairOrderEntity.getServices())
                 .items(repairOrderEntity.getItems())
                 .build();
    }

    public RepairOrderDto updateRepairOrder( RepairOrderDto repairOrderDto) {
        Optional<RepairOrder> repairOrderOptional = repairOrderRepository.findById(repairOrderDto.getRepairOrderId());
        RepairOrder repairOrderEntity = null;
        if (repairOrderOptional.isPresent()) {
            repairOrderEntity = repairOrderOptional.get();
            if (repairOrderDto.getDescription() != null)
                repairOrderEntity.setDescription(repairOrderDto.getDescription());
            if (repairOrderDto.getServiceDate() != null)
                repairOrderEntity.setServiceDate(repairOrderDto.getServiceDate());
            if (repairOrderDto.getCustomerId() != null)
                repairOrderEntity.setCustomerId(repairOrderDto.getCustomerId());
            if (repairOrderDto.getMechanicId() != null)
                repairOrderEntity.setMechanicId(repairOrderDto.getMechanicId());
            if (!repairOrderDto.getServices().isEmpty()) {
                repairOrderEntity.getServices().clear();
                repairOrderEntity.getServices().addAll(repairOrderDto.getServices());
            }
            if (!repairOrderDto.getItems().isEmpty()) {
                repairOrderEntity.getItems().clear();
                repairOrderEntity.getItems().addAll(repairOrderDto.getItems());
            }
            repairOrderRepository.save(repairOrderEntity);
        }
        return RepairOrderDto.builder()
                .repairOrderId(repairOrderEntity.getRepairOrderId())
                .description(repairOrderEntity.getDescription())
                .serviceDate(repairOrderEntity.getServiceDate())
                .customerId(repairOrderEntity.getCustomerId())
                .mechanicId(repairOrderEntity.getMechanicId())
                .services(repairOrderEntity.getServices())
                .items(repairOrderEntity.getItems())
                .build();
    }
    public void deleteRepairOrder(Integer id){
        repairOrderRepository.deleteById(id);
    }

}
