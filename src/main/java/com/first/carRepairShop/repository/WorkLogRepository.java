package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.WorkLog;
import com.first.carRepairShop.entity.WorkLogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog,Integer> {


    Long countByCreateDate(LocalDate createDate);
    List<WorkLog> findAllByWorkLogStatus(WorkLogStatus workLogStatus);
    List<WorkLog> findByCreateDateBetween(LocalDate from ,LocalDate to);

}
