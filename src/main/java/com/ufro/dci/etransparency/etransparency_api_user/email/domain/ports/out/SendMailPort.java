package com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;

public interface SendMailPort {
    void send(MailMessage message);
}
