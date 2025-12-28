package com.codeshutlle.project.JWTAndOAuth2.utils;

import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.repository.UserRepository;
import com.codeshutlle.project.JWTAndOAuth2.service.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User auth2User = (DefaultOAuth2User) authenticationToken.getPrincipal();
        String email=auth2User.getAttribute("email");
        User user=userRepository.findUserByEmail(email);


        if(user == null){
            User newUser = User.builder()
                    .email(auth2User.getAttribute("email"))
                    .name(auth2User.getAttribute("name"))
                    .build();
            user=userRepository.save(newUser);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;

        getRedirectStrategy().sendRedirect(request,response,frontEndUrl);
    }
}
