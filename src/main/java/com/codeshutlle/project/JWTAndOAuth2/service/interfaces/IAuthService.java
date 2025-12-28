package com.codeshutlle.project.JWTAndOAuth2.service.interfaces;

import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginResponseDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.UserDTO;
import org.jspecify.annotations.Nullable;

public interface IAuthService {
    public UserDTO signupUser(UserDTO userDTO);
    public LoginResponseDTO loginUser(LoginDTO loginDTO);


    @Nullable LoginResponseDTO findUserBasedRefresheToken(String refreshToken);
}
