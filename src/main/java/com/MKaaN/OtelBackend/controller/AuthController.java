package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.dto.ApiResponse;
import com.MKaaN.OtelBackend.dto.JwtResponse;
import com.MKaaN.OtelBackend.dto.LoginRequest;
import com.MKaaN.OtelBackend.dto.RegisterRequest;
import com.MKaaN.OtelBackend.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse response = authService.login(loginRequest);
            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.unauthorized("Geçersiz kullanıcı adı veya şifre");
        }
    }

    @PostMapping("/register")
    public ApiResponse<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            JwtResponse response = authService.register(registerRequest);
            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Kayıt işlemi başarısız: " + e.getMessage());
        }
    }
}