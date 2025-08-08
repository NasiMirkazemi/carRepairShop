package com.first.carRepairShop.repository;

import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.entity.TokenRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<TokenRefresh, Integer> {
    Optional<TokenRefresh> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUser(UserApp userApp);
}
