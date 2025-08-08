package com.first.carRepairShop.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    APPOINTMENT_REMINDER("APPT_REMINDER", "Appointment Reminder"),
    APPOINTMENT_CONFIRMED("APPT_CONFIRMED","Appointment Confirmed"),
    REPAIR_COMPLETED("REPAIR_COMPLETED", "Repair Completed"),
    REPAIR_DELAY("REPAIR_DELAY", "Repair Delay"),
    PAYMENT_REMINDER("PAYMENT_REMINDER", "Payment Reminder"),
    ORDER_CONFIRMATION("ORDER_CONFIRMATION", "Order Confirmation"),
    SERVICE_OFFER("SERVICE_OFFER", "Special Service Offer"),
    MAINTENANCE_REMINDER("MAINT_REMINDER", "Maintenance Reminder"),
    WARRANTY_EXPIRATION("WARRANTY_EXPIRATION", "Warranty Expiration"),
    FEEDBACK_REQUEST("FEEDBACK_REQUEST", "Feedback Request"),
    NEW_PARTS_AVAILABLE("NEW_PARTS_AVAILABLE", "New Parts Available"),
    REPAIR_ESTIMATE("REPAIR_ESTIMATE", "Repair Estimate"),
    PAYMENT_RECEIVED("PAYMENT_RECEIVED", "Payment Received"),
    INVOICE_READY("INVOICE_READY", "Invoice Ready"),
    CANCELLATION_CONFIRMATION("CANCELLATION_CONFIRM", "Order Cancellation Confirmation"),
    ORDER_SHIPPED("ORDER_SHIPPED", "Order Shipped");

    private final String value;
    private final String description;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
