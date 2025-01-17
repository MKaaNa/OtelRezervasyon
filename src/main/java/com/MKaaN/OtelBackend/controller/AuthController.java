package com.MKaaN.OtelBackend.controller;

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
    public ResponseEntity<String> login(@RequestBody User user) {
        // Kullanıcıyı email ile bulma
        User existingUser = authService.getUserByEmail(user.getEmail());  // getEmail() metodunu kullanıyoruz

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.registerUser(user);  // Kullanıcıyı kaydet
        return ResponseEntity.ok("Registration successful");
    }
}