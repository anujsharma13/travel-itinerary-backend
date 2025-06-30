package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.JwtHelper;
import org.springframework.stereotype.Component;

/*
key ->value
 */
@Component
public class JwtAuthFilter {
    private final JwtHelper jwtHelper;

    public JwtAuthFilter(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        final String authHeader = request.getHeader("Authorization");
        String jwt;
        String username;
        if(authHeader== null || !authHeader.startsWith("Bearer ")){

        }
        jwt = authHeader.substring(7);
        username = jwtHelper.extractUserName(jwt);
    }
}
