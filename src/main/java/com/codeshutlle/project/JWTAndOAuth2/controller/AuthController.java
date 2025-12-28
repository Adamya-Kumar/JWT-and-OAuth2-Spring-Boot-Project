package com.codeshutlle.project.JWTAndOAuth2.controller;

import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginResponseDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.UserDTO;
import com.codeshutlle.project.JWTAndOAuth2.service.interfaces.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authService.signupUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginuser(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        LoginResponseDTO responseDTO=authService.loginUser(loginDTO);
        Cookie cookie=new Cookie("refreshToken",responseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresher(HttpServletRequest request){
       String refreshToken = String.valueOf(Arrays.stream(request.getCookies())
                .filter(cookie ->
                        "refreshToken".equals(cookie.getName())).findFirst()
                       .map(Cookie::getValue)
               .orElseThrow(()->
                new ExpressionException("Not found")));

        return ResponseEntity.ok(authService.findUserBasedRefresheToken(refreshToken));
    }
}
