package com.example.microservice.app.global.security;

import com.example.microservice.app.global.property.GlobalProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtTokenService {
    private final GlobalProperties globalProperties;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", "ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h active
                .signWith(SignatureAlgorithm.HS256, globalProperties.getAuthSecretKey().getBytes())
                .compact();
    }
}