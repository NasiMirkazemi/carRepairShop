package com.first.carRepairShop.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {


    UserDetails authenticate(String username, String password);


}
