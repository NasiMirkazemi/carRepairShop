package com.first.carRepairShop.repository;

import com.first.carRepairShop.dto.UserDto;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    boolean existsByName(String role);

    Optional<Role> findByName(String name);

}
