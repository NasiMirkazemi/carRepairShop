package com.first.carRepairShop.security;

import com.first.carRepairShop.entity.UserApp;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class UserAppDetail implements UserDetails {
    private final UserApp userApp;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userApp.getRole().getPermissions().stream()
                //It converts a permission name into a Spring Security authority object used for authorization decisions.
                .map(permissions -> new SimpleGrantedAuthority(permissions.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userApp.getPassword();
    }

    @Override
    public String getUsername() {
        return userApp.getUsername();
    }


    public String getRole() {
        return userApp.getRole().getName();
    }

    public Integer getId() {
        return userApp.getId();
    }

    public String getName() {
        return userApp.getName();
    }

    public String getLastname() {
        return userApp.getLastname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userApp.isEnable();
    }


}
