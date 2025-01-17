package com.MKaaN.OtelBackend.services.auth;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void registerUser(User user) {
        // Şifreyi bcrypt ile şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String loginUser(String email, String password) {
        User user = getUserByEmail(email);  // Email'e göre kullanıcıyı al

        // Şifreyi kontrol et
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Geçersiz şifre");
        }

        // JWT token üret
        return generateToken(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getUserRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 saatlik geçerlilik süresi
                .signWith(SignatureAlgorithm.HS256, "secret_key") // Burada "secret_key" ile imzalıyoruz
                .compact();
    }
}