package com.MKaaN.OtelBackend.services.auth;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Kullanıcıyı email ile alıyoruz
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));  // Optional<User> döndüren metod
        return optionalUser.orElse(null);  // Eğer Optional boş ise null döner
    }

    // Şifre doğrulaması
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword); // Şifreyi kontrol et
    }

    // Admin hesabı oluşturma
    public void createDefaultAdmin() {
        Optional<User> existingAdmin = Optional.ofNullable(userRepository.findByEmail("admink@test.com"));

        if (existingAdmin.isEmpty()) {
            // Şifreyi hash'liyoruz
            String encodedPassword = passwordEncoder.encode("admin1234");

            // Yeni admin kullanıcı oluşturuyoruz
            User admin = new User();
            admin.setEmail("admink@test.com");
            admin.setName("Admin");
            admin.setPassword(encodedPassword);
            admin.setUserRole(UserRole.ADMIN);

            // Admin hesabını kaydediyoruz
            userRepository.save(admin);
            System.out.println("Default admin created successfully.");
        } else {
            System.out.println("Admin already exists.");
        }
    }

    // Kullanıcı giriş işlemi
    public String loginUser(String email, String password) {
        User user = getUserByEmail(email);  // Kullanıcıyı email ile buluyoruz

        if (user == null || !checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");  // Kullanıcı yoksa ya da şifre yanlışsa hata fırlat
        }

        // Token oluşturuluyor
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email);
        claims.put("name", user.getName());  // Kullanıcı adı ekleniyor
        claims.put("created", new Date());
        claims.put("role", user.getUserRole().name());  // Kullanıcının rolünü ekleniyor

        // Anahtar boyutunun yeterli olduğundan emin olmak için secretKeyFor() metodunu kullanalım
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token'ın geçerlilik süresi (10 saat)
                .signWith(key)
                .compact();

        return token;  // Token'ı döndürüyoruz
    }

    // Kullanıcı kaydetme işlemi
    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user); // Yeni kullanıcıyı kaydediyoruz
    }
}