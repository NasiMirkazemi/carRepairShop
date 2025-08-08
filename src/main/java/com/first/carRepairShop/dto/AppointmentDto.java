package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.AppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure compatibility for deserialization

    private Integer appointmentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String appointmentNumber;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    private Integer customerId;


    @NotNull(message = "Car ID is required")
    @Positive(message = "Car ID must be a positive number")
    private Integer carId;

    @NotNull(message = "Appointment date  is required")
    @FutureOrPresent(message = "Appointment date must be now or in the future ")
    private LocalDate appointmentDate;
    @NotNull(message = "Appointment  time is required")
    private LocalTime appointmentTime;

    private AppointmentStatus appointmentStatus;

    @Size(max = 255, message = "Notes cannot exceed 255 characters")
    @JsonProperty("note")  // Ensure Jackson maps this field

    private String notes;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentId=" + appointmentId +
                ", appointmentNumber='" + appointmentNumber + '\'' +
                ", customerId=" + customerId +
                ", carId=" + carId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", appointmentStatus=" + appointmentStatus +
                ", notes='" + notes + '\'' +
                '}';
    }
}
