package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

public interface RecoveryService {

    public String sendRecoveryCode(String email);

    public String validateRecoveryCode(String recoveryCode);

    public String validateNewPassword(String password, String validationPassword);
    
}
