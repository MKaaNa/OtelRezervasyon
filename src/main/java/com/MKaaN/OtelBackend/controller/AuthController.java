package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.utils.JwtResponse;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        User existingUser = authService.getUserByEmail(user.getEmail());

        if (existingUser != null && authService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            String token = authService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(new JwtResponse(token)); // Token'ı döndürüyoruz
        } else {
            return ResponseEntity.status(401).body(new JwtResponse("Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}