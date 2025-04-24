package com.example.microservice.app.global.security;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Hidden
public class AuthController {
    private final JwtTokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        //TODO e.g. validation with password - depends on needs
        return "Bearer " + tokenService.generateToken(username);
    }
}