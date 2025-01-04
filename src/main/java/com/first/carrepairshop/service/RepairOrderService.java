package com.first.carrepairshop.service;

import com.first.carrepairshop.dto.RepairOrderDto;
import com.first.carrepairshop.entity.RepairOrder;
import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.mapper.RepairOrderMapper;
import com.first.carrepairshop.mapper.ServiceMapper;
import com.first.carrepairshop.repository.RepairOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairOrderService {
    private final RepairOrderRepository repairOrderRepository;
    private final RepairOrderMapper repairOrderMapper;
    private final ServiceMapper serviceMapper;

    public RepairOrderDto addRepairOrder(RepairOrderDto repairOrderDto) {
        RepairOrder repairOrderEntity = repairOrderRepository.save(repairOrderMapper.toRepairOrderEntity(repairOrderDto));
        return repairOrderMapper.toRepairOrderDto(repairOrderEntity);
    }

    public RepairOrderDto getRepairOrder(Integer id) {
        RepairOrder repairOrderEntity = repairOrderRepository.findById(id).get();
        return repairOrderMapper.toRepairOrderDto(repairOrderEntity);
    }

    public RepairOrderDto updateRepairOrder(RepairOrderDto repairOrderDto) {
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
            if (!repairOrderDto.getServicesDto().isEmpty()) {
                repairOrderEntity.getServices().clear();
                repairOrderEntity.getServices().addAll(serviceMapper.toEntityList(repairOrderDto.getServicesDto()));
            }

            repairOrderRepository.save(repairOrderEntity);
        }
        return repairOrderMapper.toRepairOrderDto(repairOrderEntity);
    }

    public void deleteRepairOrder(Integer id) {
        repairOrderRepository.deleteById(id);
    }

}
