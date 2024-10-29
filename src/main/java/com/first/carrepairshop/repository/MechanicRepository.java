package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic,Integer> {
}
