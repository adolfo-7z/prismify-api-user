package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in;

public interface CreateUserIfNotExistsUseCase {
    void createAdminIfMissing(String username, String email, String password);
}
