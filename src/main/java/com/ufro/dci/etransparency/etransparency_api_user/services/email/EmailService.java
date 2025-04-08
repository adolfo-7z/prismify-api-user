package com.ufro.dci.etransparency.etransparency_api_user.services.email;

import java.io.IOException;
import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendHtmlEmail(String to, String subject, String message) throws MessagingException, IOException;

}
