package com.codeshutlle.project.JWTAndOAuth2.service.interfaces;

import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public com.codeshutlle.project.JWTAndOAuth2.entity.User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new ExpressionException("User not found"));
    }


}
