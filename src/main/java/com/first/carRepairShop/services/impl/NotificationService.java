package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.AssignmentRepository;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final AssignmentRepository assignmentRepository;
    private final MechanicRepository mechanicRepository;

    @Transactional
    public void sendConfirmAppointmentNotification(
            NotificationType type,
            String customerName,
            String customerLastname, String email,
            LocalDate appointmentDate,
            LocalTime appointmentTime) {
        NotificationMessage notificationMessage = new NotificationMessage(
                type, customerName, customerLastname,
                null, appointmentDate, appointmentTime, null, null);
        String message = notificationMessage.generateMessage();
        Notification notification = new Notification();
        notification.setType(type);
        notification.setMessage(message);
        try {
            emailService.sendEmail(email, type, message);
            notification.setDelivered(true);
        } catch (MessagingException e) {
            System.out.println("Failed to send email to " + ": " + e.getMessage());
            notification.setDelivered(false);
        } finally {
            notificationRepository.save(notification);
        }
    }

    public void notifyMechanicAssignment(Assignment assignment){
        if (assignment==null)
            throw new BadRequestException("Assignment is required");
         Mechanic mechanic=mechanicRepository.findById(assignment.getMechanicId())
                .orElseThrow(()-> new NotFoundException("No Mechanic found for id:"+assignment.getMechanicId()));

    }

   /* public static Notification createAppointmentReminder(Customer customer, LocalDateTime appointmentTime) {
        String repairOrderNumber = customer.getRepairOrders().isEmpty() ? "N/A"
                : customer.getRepairOrders().get(customer.getRepairOrders().size() - 1).getRepairOrderNumber();
        NotificationMessage message1 = new NotificationMessage(NotificationType.APPOINTMENT_REMINDER,
                customer.getName(), repairOrderNumber,
                appointmentTime, null);
        return new Notification(customer, NotificationType.APPOINTMENT_REMINDER, message1.generateMessage());
    }

    public static Notification createPaymentReminder(Customer customer, LocalDateTime dueDate) {
        String repairOrderNumber = customer.getRepairOrders().isEmpty() ? "N/A"
                : customer.getRepairOrders().get(customer.getRepairOrders().size() - 1).getRepairOrderNumber();
        NotificationMessage message1 = new NotificationMessage(NotificationType.PAYMENT_REMINDER,
                customer.getName(), repairOrderNumber, null, dueDate);
        return new Notification(customer, NotificationType.PAYMENT_REMINDER, message1.generateMessage());
    }

    public static Notification createGeneralNotification(Customer customer, NotificationType notificationType) {
        String repairOrderNumber = customer.getRepairOrders().isEmpty() ? "N/A"
                : customer.getRepairOrders().get(customer.getRepairOrders().size() - 1).getRepairOrderNumber();
        NotificationMessage message1 =
                new NotificationMessage(notificationType, customer.getName(), repairOrderNumber, null, null);
        return new Notification(customer, notificationType, message1.generateMessage());
    }
*/
}
