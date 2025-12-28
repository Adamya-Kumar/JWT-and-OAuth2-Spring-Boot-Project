package com.codeshutlle.project.JWTAndOAuth2.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoginResponseDTO {
    private LocalDateTime timeStamp;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDTO() {
        this.timeStamp = LocalDateTime.now();
    }

    public LoginResponseDTO(String accessToken, String refreshToken) {
        this();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
