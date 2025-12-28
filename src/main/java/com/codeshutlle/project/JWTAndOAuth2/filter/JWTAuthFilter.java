package com.codeshutlle.project.JWTAndOAuth2.filter;

import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import com.codeshutlle.project.JWTAndOAuth2.service.JWTService;
import com.codeshutlle.project.JWTAndOAuth2.service.interfaces.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
   private final JWTService jwtService;
   private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String path = request.getServletPath();
           // SKIP filter logic for Swagger internal paths
           if (path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
               filterChain.doFilter(request, response);
               return;
           }

           final String requestTokenHeader = request.getHeader("Authorization");
           if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
               filterChain.doFilter(request, response);
               return;
           }

           String token = requestTokenHeader.split("Bearer ")[1];

           Long id = jwtService.getUserIdByVerifyJwToken(token);
           if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               User user = userService.getUserById(id);
               UsernamePasswordAuthenticationToken authenticationToken = new
                       UsernamePasswordAuthenticationToken(user, null, null);
               authenticationToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
           filterChain.doFilter(request, response);
       }catch (Exception e){
           handlerExceptionResolver.resolveException(request,response,null,e);
       }
    }
}
