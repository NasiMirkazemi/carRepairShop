package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Mechanic;
import com.first.carRepairShop.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {
    @Query("select w from WorkLog w where w.mechanic.id =:id")
    List<WorkLog> findWorkLogByMechanicId(@Param("id") Integer id);

}
