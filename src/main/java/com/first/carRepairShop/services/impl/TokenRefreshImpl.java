package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.controller.LogoutRequest;
import com.first.carRepairShop.dto.LoginResponse;
import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.TokenRefreshRepository;
import com.first.carRepairShop.repository.UserRepository;
import com.first.carRepairShop.security.JwtUtil;
import com.first.carRepairShop.entity.TokenRefresh;
import com.first.carRepairShop.services.TokenRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenRefreshImpl implements TokenRefreshService {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @Value("${refresh.token.expiry}")
    private Long refreshTokenDurationMs;
    private TokenRefreshRepository tokenRefreshRepository;
    private UserRepository userRepository;

    @Override
    public String createRefreshToken(Integer userId) {
        UserApp userApp = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No User found with id: " + userId));
        TokenRefresh tokenRefresh = TokenRefresh.builder()
                .user(userApp)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .token(UUID.randomUUID().toString())
                .build();
        tokenRefreshRepository.save(tokenRefresh);
        return tokenRefresh.getToken();

    }

    @Override
    public Optional<TokenRefresh> findByToken(String token) {
        return tokenRefreshRepository.findByToken(token);
    }

    @Override
    public TokenRefresh verifyExpiration(TokenRefresh token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            tokenRefreshRepository.delete(token);
            throw new RuntimeException("Refreshed Token is Expired");
        }
        return token;
    }

    @Override
    public LoginResponse refreshAccessToken(String refreshToken) {
        TokenRefresh tokenRefresh1 = findByToken(refreshToken)
                .map(this::verifyExpiration)
                .orElseThrow(() -> new NotFoundException("Invalid  refresh Token"));
        UserApp userApp = tokenRefresh1.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userApp.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(tokenRefresh1.getToken())
                .username(userApp.getUsername())
                .userId(userApp.getId())
                .build();

    }

    @Override
    public void deleteByToken(LogoutRequest logoutRequest) {
        if (logoutRequest == null) throw new BadRequestException("Logout request is required");

        tokenRefreshRepository.deleteByToken(logoutRequest.getRefreshToken());
        log.info("Refresh Token has been deleted");
    }
}
