package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Appointment;
import com.first.carRepairShop.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    boolean existsByCustomerIdAndCarIdAndAppointmentDate(Integer customerId, Integer carId, LocalDate appointmentDate);
    long countByAppointmentDate(LocalDate appointmentDate);
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
    @Query("select a.appointmentTime from Appointment a where a.appointmentDate=:appointmentDate and a.appointmentStatus=:status")
    List<LocalTime> findBookTimeSlots(@Param("appointmentDate") LocalDate appointmentDate, @Param("status")AppointmentStatus status);
    void deleteByAppointmentDateAndAndAppointmentTime(LocalDate appointmentDate,LocalTime appointmentTime);
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);
    List<Appointment> findByCustomerId(Integer customerId);
    List<Appointment> findByCarId(Integer carId);
    void deleteByCarId(Integer carId);
    void deleteByCustomerId(Integer customerId);
}
