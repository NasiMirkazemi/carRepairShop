package com.first.carRepairShop.controller;

import com.first.carRepairShop.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User has been deleted with id :" + userId);
    }


}
