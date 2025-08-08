package com.first.carRepairShop.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor


public class NotificationMessage {

    private final NotificationType notificationType;
    private final String customerName;
    private final String customerLastname;
    private final String orderOrderNumber;
    private final LocalDate appointmentDate;
    private final LocalTime appointmentTime;
    private final LocalDate dueDate;
    private final LocalTime dueTime;


    public static String formatDateTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            return "Not Available";
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return date.format(dateFormatter) + " " + time.format(timeFormatter);
    }
    	//1.	Creates a multi-line message (email body) using triple quotes """ ... """.
    	/*2.	Uses %s placeholders to insert:
            •	%s %s: full name (first name and last name)
	•	%s: the formatted appointment date & time
        */


    public String generateMessage() {
        switch (notificationType) {
            case APPOINTMENT_REMINDER:
                return String.format("""
                        Dear %s %s,

                        We are pleased to confirm your appointment scheduled for %s.

                        If you have any questions or need to make changes, please feel free to contact us.

                        Thank you for choosing our services.

                        Sincerely,  
                        Car Repair Shop Team
                        """, customerName, customerLastname, formatDateTime(appointmentDate, appointmentTime));

            case APPOINTMENT_CONFIRMED:
                return String.format("""
                        Hello %s %s,

                        Your appointment is confirmed for %s.

                        Thank you for choosing us!

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName != null ? customerName : "", customerLastname != null ? customerLastname : "", formatDateTime(appointmentDate, appointmentTime));

            case REPAIR_COMPLETED:
                return String.format("""
                        Dear %s,

                        Your repair for order %s was completed on %s.

                        Please visit us to pick up your vehicle.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case REPAIR_DELAY:
                return String.format("""
                        Dear %s,

                        Your repair for order %s has been delayed.

                        New estimated completion date: %s.

                        We apologize for the inconvenience.

                        Sincerely,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(dueDate, dueTime));

            case PAYMENT_REMINDER:
                return String.format("""
                        Dear %s,

                        Your payment for order %s is due by %s.

                        Please complete the payment to avoid delays.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(dueDate, dueTime));

            case ORDER_CONFIRMATION:
                return String.format("""
                        Thank you %s,

                        Your order %s was confirmed on %s.

                        We will notify you once it's shipped.

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case SERVICE_OFFER:
                return String.format("""
                        Dear %s,

                        We are offering a special service discount valid until %s.

                        Contact us for more details!

                        Sincerely,  
                        Car Repair Shop Team
                        """, customerName, formatDateTime(dueDate, dueTime));

            case MAINTENANCE_REMINDER:
                return String.format("""
                        Hello %s,

                        It's time for your vehicle's maintenance.

                        Your last service was on %s.

                        Please schedule an appointment soon.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, formatDateTime(appointmentDate, appointmentTime));

            case WARRANTY_EXPIRATION:
                return String.format("""
                        Dear %s,

                        Your vehicle's warranty expires on %s.

                        Please contact us for renewal options.

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName, formatDateTime(dueDate, dueTime));

            case FEEDBACK_REQUEST:
                return String.format("""
                        Dear %s,

                        We would appreciate your feedback on your recent service on %s.

                        Please take a moment to fill out our survey.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, formatDateTime(appointmentDate, appointmentTime));

            case NEW_PARTS_AVAILABLE:
                return String.format("""
                        Hello %s,

                        New parts will be available starting from %s.

                        Contact us for details and availability.

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName, formatDateTime(dueDate, dueTime));

            case REPAIR_ESTIMATE:
                return String.format("""
                        Dear %s,

                        The estimated cost for your repair for order %s was prepared on %s.

                        Please approve the estimate before we proceed.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case PAYMENT_RECEIVED:
                return String.format("""
                        Dear %s,

                        We have received your payment for order %s on %s.

                        Thank you for your timely payment.

                        Sincerely,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case INVOICE_READY:
                return String.format("""
                        Dear %s,

                        Your invoice for order %s was generated on %s.

                        You can view it in your account.

                        Thank you,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case CANCELLATION_CONFIRMATION:
                return String.format("""
                        Dear %s,

                        Your order %s was successfully canceled on %s.

                        We hope to serve you in the future.

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            case ORDER_SHIPPED:
                return String.format("""
                        Dear %s,

                        Your order %s was shipped on %s and is on its way.

                        Thank you for your business!

                        Sincerely,  
                        Car Repair Shop Team
                        """, customerName, orderOrderNumber, formatDateTime(appointmentDate, appointmentTime));

            default:
                return String.format("""
                        Dear %s,

                        You have a new notification.

                        Please check your account for details.

                        Best regards,  
                        Car Repair Shop Team
                        """, customerName);
        }
    }}
