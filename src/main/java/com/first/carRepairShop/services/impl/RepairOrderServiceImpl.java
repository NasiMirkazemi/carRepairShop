package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.RepairOrderDto;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.ItemDetailMapper;
import com.first.carRepairShop.mapper.RepairOrderMapper;
import com.first.carRepairShop.mapper.ServiceDetailMapper;
import com.first.carRepairShop.repository.*;
import com.first.carRepairShop.services.RepairOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepairOrderServiceImpl implements RepairOrderService {
    private final RepairOrderRepository repairOrderRepository;
    private final RepairOrderMapper repairOrderMapper;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final ItemDetailMapper itemDetailMapper;
    private final ServiceDetailMapper serviceDetailMapper;
    private final Logger logger = LoggerFactory.getLogger(RepairOrderServiceImpl.class);


    public RepairOrder toRepairOrderEntity(RepairOrderDto repairOrderDto) {
        if (repairOrderDto == null)
            throw new BadRequestException("Repair Order info is required");
        RepairOrder repairOrder = repairOrderMapper.toEntityFromDto(repairOrderDto);
        Customer customer = customerRepository.findById(repairOrderDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("customer whit id :" + repairOrderDto.getCustomerId() + "not found"));
        customer.getRepairOrders().add(repairOrder);
        Car car = carRepository.findById(repairOrderDto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("car with id:" + repairOrderDto.getCarId() + "not found"));
        repairOrder.setCustomer(customer);
        repairOrder.setCar(car);
        return repairOrder;
    }

    @Transactional
    public RepairOrderDto addRepairOrder(RepairOrderDto repairOrderDto) {
        if (repairOrderDto == null) {
            throw new BadRequestException("RepairOrder can is required");
        }
        RepairOrder repairOrder = toRepairOrderEntity(repairOrderDto);
        repairOrder.setRepairStatus(RepairStatus.NEW);
        repairOrder.setCreateDate(LocalDate.now());
        RepairOrder savedRepairOrder = repairOrderRepository.save(repairOrder);
        return repairOrderMapper.toDto(savedRepairOrder);

    }

    @Transactional
    public RepairOrderDto updateRepairOrder(RepairOrderDto repairOrderDto) {
        if (repairOrderDto == null) {
            throw new BadRequestException("repair order is required");
        }
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderDto.getRepairOrderId())
                .orElseThrow(() -> new NotFoundException("No repairOrder found with this id:" + repairOrderDto.getRepairOrderId()));
        Optional.ofNullable(repairOrderDto.getRepairOrderNumber()).ifPresent(repairOrder::setRepairOrderNumber);
        Optional.ofNullable(repairOrderDto.getDescription()).ifPresent(repairOrder::setDescription);
        Optional.ofNullable(repairOrderDto.getRepairStatus())
                .map(String::toUpperCase)
                .map(RepairStatus::valueOf)
                .ifPresent(repairOrder::setRepairStatus);

        Customer customer = customerRepository.findById(repairOrderDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("No Customer found with id:" + repairOrderDto.getCustomerId()));
        repairOrder.setCustomer(customer);
        Car car = carRepository.findById(repairOrderDto.getCarId())
                .orElseThrow(() -> new NotFoundException("No Car found whit id:" + repairOrderDto.getCarId()));
        repairOrder.setCar(car);
        if (repairOrderDto.getPlannedServices() != null && !repairOrderDto.getPlannedServices().isEmpty()) {
            repairOrderDto.getPlannedServices().stream()
                    .forEach(serviceDetailDto -> {
                        Optional<ServiceDetail> serviceDetailExist = repairOrder.getPlannedServices().stream()
                                .filter(serviceDetail -> serviceDetailDto.getServiceId() != null &&
                                        serviceDetailDto.getServiceId().equals(serviceDetail.getServiceId()))
                                .findFirst();
                        serviceDetailExist.ifPresentOrElse(serviceDetail -> serviceDetailMapper
                                .updateServiceDetailFromDto(serviceDetailDto, serviceDetail), () -> {
                            ServiceDetail serviceDetailNew = serviceDetailMapper.toServiceDetail(serviceDetailDto);
                            repairOrder.getPlannedServices().add(serviceDetailNew);
                        });
                    });
        }
        if (repairOrderDto.getPlannedItems() != null && !repairOrderDto.getPlannedItems().isEmpty()) {
            repairOrderDto.getPlannedItems().stream()
                    .forEach(itemDetailDto -> {
                                Optional<ItemDetail> itemDetailExist = repairOrder.getPlannedItems().stream()
                                        .filter(itemDetail -> itemDetailDto.getItemId() != null &&
                                                itemDetailDto.getItemId().equals(itemDetail.getItemId()))
                                        .findFirst();
                                itemDetailExist.ifPresentOrElse(itemDetail -> itemDetailMapper
                                        .updateItemDetailFromItemDetailDto(itemDetailDto, itemDetail), () -> {
                                    ItemDetail newItemDetail = itemDetailMapper.toItemDetail(itemDetailDto);
                                    repairOrder.getPlannedItems().add(newItemDetail);
                                });
                            }
                    );
        }
        RepairOrder updatedRepairOrder = repairOrderRepository.save(repairOrder);
        return repairOrderMapper.toDto(updatedRepairOrder);

    }

    public RepairOrderDto getRepairOrder(Integer repairOrderId) {
        if (repairOrderId == null) {
            throw new BadRequestException("repairOrder id is required");
        }
        RepairOrder retrieveRepairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new NotFoundException("No repairOrder found with id:" + repairOrderId));
        return repairOrderMapper.toDto(retrieveRepairOrder);
    }

    @Transactional
    public void deleteRepairOrder(Integer repairOrderId) {
        if (repairOrderId == null) {
            throw new BadRequestException("Repair Order id is required");
        }
        RepairOrder retriveRepairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new NotFoundException("No RepairOrder found whit id:" + repairOrderId));
        repairOrderRepository.delete(retriveRepairOrder);
        logger.info("Successfully deleted RepairOrder with id:" + repairOrderId);

    }

    public RepairOrderDto trackRepairOrder(Integer repairOrderId) {
        return null;
    }

    @Override
    public List<RepairOrderDto> getByCustomerId(Integer customerId) {
        if (customerId == null) throw new BadRequestException("Customer id is required");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("NO Customer found with id: " + customerId));
        List<RepairOrder> repairOrderList = customer.getRepairOrders();
        return repairOrderList.stream()
                .map(repairOrderMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<RepairOrderDto> getAllByCarId(Integer carId) {
        if (carId == null) throw new BadRequestException("Car id is required");
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("No Car found with id: " + carId));
        List<RepairOrder> repairOrderList = repairOrderRepository.findAllByCar_CarId(car.getCarId());
        return repairOrderList.stream()
                .map(repairOrderMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<RepairOrderDto> getAllByCarNumberPlate(String numberPlate) {
        if (numberPlate == null) throw new BadRequestException("Number plate is required");
        Car car = carRepository.findByNumberPlate(numberPlate)
                .orElseThrow(() -> new NotFoundException("No Car found with Number plate : " + numberPlate));
        List<RepairOrder> repairOrderList = repairOrderRepository.findAllByCar_CarId(car.getCarId());
        return repairOrderList.stream()
                .map(repairOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RepairOrderDto updateRepairOrderStatus(Integer repairOrderId, String repairOrderStatus) {
        if (repairOrderId == null) throw new BadRequestException("RepairOrder id is required");
        if (repairOrderStatus == null) throw new BadRequestException("Status is required");
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new NotFoundException("No Repair Order found with id: " + repairOrderId));
        repairOrder.setRepairStatus(RepairStatus.valueOf(repairOrderStatus.toUpperCase()));
        RepairOrder savedRepairOrder = repairOrderRepository.save(repairOrder);
        return repairOrderMapper.toDto(savedRepairOrder);
    }


}
