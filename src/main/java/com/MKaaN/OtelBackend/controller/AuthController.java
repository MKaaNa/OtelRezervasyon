package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.utils.JwtResponse;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            String userRole = existingUser.getUserRole().name();
            return ResponseEntity.ok(new JwtResponse(token, userRole)); // Token'ı döndürüyoruz
        } else {
            return ResponseEntity.status(401).body(new JwtResponse("Invalid credentials","Invalid credentials"));
        }
    }


    @GetMapping(value = "user-info")
    public ResponseEntity<User> getUserInfo(@RequestParam String email) {
        User user = authService.getUserByEmail(email); // Email ile kullanıcıyı sorgula
        if (user != null) {
            return ResponseEntity.ok(user); // Kullanıcı bilgilerini döndür
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Kullanıcı bulunamazsa 404 döndür
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}