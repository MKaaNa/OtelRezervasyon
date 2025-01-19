package com.MKaaN.OtelBackend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Kullanıcıyı email ile alıyoruz
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);  // Eğer kullanıcı yoksa null döner
    }

    // Şifre doğrulaması
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // JWT token oluşturma işlemi
    public String loginUser(String email, String password) {
        // Kullanıcıyı al
        User user = getUserByEmail(email);

        if (user == null || !checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials"); // Kullanıcı bulunamadı veya şifre yanlış
        }

        // Token oluşturuluyor
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email);  // Subject olarak e-posta adresini koyuyoruz
        claims.put("created", new Date());

        // Anahtar boyutunun yeterli olduğundan emin olmak için secretKeyFor() metodunu kullanalım
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // HS256 için güvenli anahtar oluştur

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Token 10 saat geçerli olacak
                .signWith(key) // Yeni anahtar ile token'ı imzalıyoruz
                .compact();

        return token;
    }

    // Kullanıcı kaydetme işlemi
    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user); // Yeni kullanıcıyı kaydet
    }
}