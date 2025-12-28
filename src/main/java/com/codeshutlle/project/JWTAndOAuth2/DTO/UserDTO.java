package com.codeshutlle.project.JWTAndOAuth2.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
}
