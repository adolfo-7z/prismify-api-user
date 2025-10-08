package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationDTO {

    private Long id;
    private String message;
    private LocalDateTime date;
    
}
