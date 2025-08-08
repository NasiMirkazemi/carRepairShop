package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permissions,Integer> {

    boolean existsByName(String name);
}
