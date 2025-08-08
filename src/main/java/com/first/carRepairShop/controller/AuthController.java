package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.*;
import com.first.carRepairShop.dto.TokenRefreshRequest;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.security.JwtUtil;
import com.first.carRepairShop.security.UserAppDetail;
import com.first.carRepairShop.services.AuthenticationService;
import com.first.carRepairShop.services.MechanicService;
import com.first.carRepairShop.services.TokenRefreshService;
import com.first.carRepairShop.services.RoleService;
import com.first.carRepairShop.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final MechanicService mechanicService;
    private final TokenRefreshService tokenRefreshService;


    @PostMapping("/register/mechanic")
    public ResponseEntity<MechanicRegister> registerUser(@RequestBody @Valid MechanicRegister mechanicRegister) {
        MechanicRegister savedMechanic = mechanicService.addMechanic(mechanicRegister);
        return ResponseEntity.ok(savedMechanic);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        UserAppDetail userAppDetail = (UserAppDetail) userDetails;

        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = tokenRefreshService.createRefreshToken(((UserAppDetail) userDetails).getId());
        LoginResponse loginResponse = new LoginResponse(token
                , refreshToken
                , userAppDetail.getId()
                , userAppDetail.getUsername());
        return ResponseEntity.ok(loginResponse);

    }
    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        tokenRefreshService.deleteByToken(logoutRequest);
        return ResponseEntity.ok("Logged out successfully");

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        LoginResponse loginResponse = tokenRefreshService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(loginResponse);

    }

    @PostMapping("/addPermission/{permissionId}/{roleId}")
    public ResponseEntity<String> addPermissionToRole(@PathVariable("permissionId") Integer permissionId
            , @PathVariable("roleId") Integer roleId) {
        roleService.addPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok("Permission with id:" + permissionId + " has been add to Role with id:" + roleId);

    }

}
