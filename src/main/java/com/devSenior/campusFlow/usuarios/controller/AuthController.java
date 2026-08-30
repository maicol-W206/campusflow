package com.devSenior.campusFlow.usuarios.controller;

import com.devSenior.campusFlow.usuarios.dto.AuthResponse;
import com.devSenior.campusFlow.usuarios.dto.LoginRequest;
import com.devSenior.campusFlow.usuarios.dto.RegistroRequest;
import com.devSenior.campusFlow.usuarios.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegistroRequest request) {
        return authService.registrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}