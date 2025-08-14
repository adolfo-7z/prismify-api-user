package com.ufro.dci.etransparency.etransparency_api_user.config.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;

import java.io.Serial;
import java.util.*;

public class CustomUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient UserEntity userEntity;
    private final Set<SimpleGrantedAuthority> authorities;


    public CustomUserDetails(UserEntity user) {
        this.userEntity = user;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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
        return true;
    }
}
