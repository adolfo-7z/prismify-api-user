package com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model;

public record AuthUserDetails(Long id, String username, String hashedPassword, String role, boolean isActive) {
    
}
