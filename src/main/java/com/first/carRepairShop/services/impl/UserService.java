package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.LoginRequest;
import com.first.carRepairShop.dto.UserDto;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.UserAlreadyExistsException;
import com.first.carRepairShop.mapper.RolesMapper;
import com.first.carRepairShop.mapper.UserMapper;
import com.first.carRepairShop.repository.RoleRepository;
import com.first.carRepairShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RolesMapper rolesMapper;


    public UserDto adduserDto(UserDto userDto) {
        if (userDto == null) {
            throw new BadRequestException("user is required");
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("Username '" + userDto.getUsername() + "' is already is taken");
        }
        UserApp user = userMapper.toEntity(userDto);
        Role role = roleRepository.findByName(userDto.getRole().getName().toUpperCase())
                .orElseThrow(() -> new NotFoundException("No Role found with name:" + userDto.getRole().getName()));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(true);
        UserApp savedUser = userRepository.save(user);
        logger.info("User with id {} is saved", savedUser.getId());
        UserDto result = userMapper.toDTo(savedUser);
        result.setPassword(null);
        return result;
    }

    public void login(LoginRequest loginRequest) {
        if (loginRequest == null)
            throw new BadRequestException("Login is required");
        UserApp userApp = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("Invalid Username or Password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), userApp.getPassword())) {
            throw new BadRequestException("Invalid Username or Password");
        }
    }


    public void deleteUser(Integer userId) {
        if (userId == null)
            throw new BadRequestException("User id is required");
        UserApp user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with id :" + userId));
        userRepository.delete(user);
    }

    public UserDto updateUser(UserDto userDto) {
        if (userDto == null) throw new BadRequestException("User info is required");
        UserApp userApp = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException("No User found with id: " + userDto.getId()));
        Optional.ofNullable(userDto.getName()).ifPresent(user -> userApp.setName(userDto.getName()));
        Optional.ofNullable(userDto.getLastname()).ifPresent(user -> userApp.setLastname(userDto.getLastname()));
        Optional.ofNullable(userDto.getEmail()).ifPresent(user -> userApp.setEmail(userDto.getEmail()));
        Optional.ofNullable(userDto.getPhone()).ifPresent(user -> userApp.setPhone(userDto.getPhone()));
        Optional.ofNullable(userDto.getAddress()).ifPresent(user -> userApp.setAddress(userDto.getAddress()));
        Optional.ofNullable(userDto.getRole()).ifPresent(user -> userApp.setRole(rolesMapper.toEntity(userDto.getRole())));
        UserApp savedUser = userRepository.save(userApp);
        return userMapper.toDTo(savedUser);
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null) throw new BadRequestException("User id is required");
        if (oldPassword == null) throw new BadRequestException("Old Password is required");
        if (newPassword == null) throw new BadRequestException("New Password is required");
        if (oldPassword.equals(newPassword))
            throw new BadRequestException("New password must be different from the old password");
        UserApp userApp = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No User found with id: " + userId));
        if (!passwordEncoder.matches(oldPassword, userApp.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        if (newPassword.length() < 8) {
            throw new BadRequestException("New password must be at least 8 characters long");
        }
        if (!containsUpperCase(newPassword) || !containsLowerCase(newPassword) || !containsNumbers(newPassword)) {
            throw new BadRequestException("New password must contain uppercase, lowercase letters and a number");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        userApp.setPassword(encodedPassword);
        userRepository.save(userApp);
        log.info("Password changed for user id {}", userId);
    }
    public void logout(String token){

    }

    private boolean containsUpperCase(String input) {
        return input.chars().anyMatch(ch -> Character.isUpperCase(ch));
    }

    private boolean containsLowerCase(String input) {
        return input.chars().anyMatch(ch -> Character.isLowerCase(ch));
    }

    private boolean containsNumbers(String input) {
        return input.chars().anyMatch(ch -> Character.isDigit(ch));
    }
}
