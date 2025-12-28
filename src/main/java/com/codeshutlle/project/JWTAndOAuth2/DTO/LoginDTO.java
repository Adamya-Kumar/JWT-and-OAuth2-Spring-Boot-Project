package com.codeshutlle.project.JWTAndOAuth2.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private final String email;
    private final String password;
}
