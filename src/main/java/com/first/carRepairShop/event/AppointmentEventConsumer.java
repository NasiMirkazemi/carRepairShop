package com.first.carRepairShop.event;

import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.NotificationType;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.CustomerRepository;
import com.first.carRepairShop.services.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentEventConsumer {
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    public void consumeEvent(AppointmentConfirmEvent event) {
        Customer customer = customerRepository.findById(event.getCustomerId())
                .orElseThrow(() -> new NotFoundException("No Customer found whit this id:" + event.getCustomerId()));
        notificationService.sendConfirmAppointmentNotification(
                NotificationType.APPOINTMENT_CONFIRMED,
                customer.getName(), customer.getLastname(),
                customer.getEmail(), event.getAppointmentDate(),
                event.getAppointmentTime());

    }
}
