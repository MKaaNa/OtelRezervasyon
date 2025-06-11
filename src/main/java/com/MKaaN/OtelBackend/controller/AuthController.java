package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.dto.request.LoginRequest;
import com.MKaaN.OtelBackend.dto.request.RegisterRequest;
import com.MKaaN.OtelBackend.dto.response.JwtResponse;
import com.MKaaN.OtelBackend.service.spec.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
