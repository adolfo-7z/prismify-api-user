package prismify.auth.domain.model;

public record AuthUserDetails(Long id, String username, String hashedPassword, String role, boolean isActive) {
    
}
