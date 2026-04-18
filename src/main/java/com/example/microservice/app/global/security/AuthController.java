package com.example.microservice.app.global.security;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Hidden
public class AuthController {
    private final JwtTokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        //TODO e.g. validation with password - depends on needs
        return ResponseEntity.ok(Map.of("Bearer", tokenService.generateToken(username)));
    }
}