package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {

    boolean existsByUsername(String username);

    Optional<UserApp> findByUsername(String username);
    List<UserApp> findAllByRole(Role role);

}
