package com.codeshutlle.project.JWTAndOAuth2.service;

import com.codeshutlle.project.JWTAndOAuth2.entity.Session;
import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_SIZE=2;

    public void generateNewSession(User user, String refreshToken){
        List<Session> userSession = sessionRepository.findByUser(user);
        if(userSession.size() == SESSION_SIZE){
            userSession.sort(Comparator.comparing(Session::getLeastUsedAt));
            Session leastRecentlyUserSession = userSession.getFirst();
            sessionRepository.delete(leastRecentlyUserSession);
        }
        Session newSession =Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();
        sessionRepository.save(newSession);
    }


    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found by refreshToken: "+refreshToken));
        session.setLeastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
