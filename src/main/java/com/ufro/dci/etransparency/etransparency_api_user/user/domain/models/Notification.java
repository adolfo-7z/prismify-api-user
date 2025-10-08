package com.ufro.dci.etransparency.etransparency_api_user.user.domain.models;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification {

    private Long id;
    private String message;
    private LocalDateTime date;
    
}
