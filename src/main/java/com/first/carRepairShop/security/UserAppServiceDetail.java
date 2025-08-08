package com.first.carRepairShop.security;

import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAppServiceDetail implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User found with username:" + username));
        System.out.println(">>> Loading user from database: " + username);
        return new UserAppDetail(userApp);

    }
}
