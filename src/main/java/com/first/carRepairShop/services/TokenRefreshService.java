package com.first.carRepairShop.services;

import com.first.carRepairShop.controller.LogoutRequest;
import com.first.carRepairShop.dto.LoginResponse;
import com.first.carRepairShop.entity.TokenRefresh;

import java.util.Optional;

public interface TokenRefreshService {
    String createRefreshToken(Integer userId);
    Optional<TokenRefresh> findByToken(String token);
    TokenRefresh verifyExpiration(TokenRefresh token);
    LoginResponse refreshAccessToken(String refreshToken);

    void deleteByToken(LogoutRequest logoutRequest);
}
