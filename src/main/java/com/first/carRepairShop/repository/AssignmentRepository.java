package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findAssignmentByMechanicId(Integer mechanicId);
}