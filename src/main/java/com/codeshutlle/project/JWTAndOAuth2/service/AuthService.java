package com.codeshutlle.project.JWTAndOAuth2.service;

import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.LoginResponseDTO;
import com.codeshutlle.project.JWTAndOAuth2.DTO.UserDTO;
import com.codeshutlle.project.JWTAndOAuth2.entity.Session;
import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.repository.UserRepository;
import com.codeshutlle.project.JWTAndOAuth2.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public UserDTO signupUser(UserDTO userDTO){
        User user =modelMapper.map(userDTO, User.class);
       User userIsExist=userRepository.findUserByEmail(user.getEmail());
        if(userIsExist == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userIsExist=userRepository.save(user);
        }
        return modelMapper.map(userIsExist,UserDTO.class);
    }

    public LoginResponseDTO loginUser(LoginDTO loginDTO){
        User user=modelMapper.map(loginDTO, User.class);
        user =userRepository.findUserByEmail(user.getEmail());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDTO(accessToken,refreshToken);
    }

    @Override
    @Transactional
    public @Nullable LoginResponseDTO findUserBasedRefresheToken(String refreshToken) {
        Long userId  =jwtService.getUserIdByVerifyJwToken(refreshToken);
        sessionService.validateSession(refreshToken);

        User user=userRepository.findById(userId).orElseThrow(()->new ExpressionException("User NOt found while verfiy the refresh token"));
               String newAccessToken =jwtService.generateAccessToken(user);
        return new LoginResponseDTO(newAccessToken,refreshToken);
    }

}
