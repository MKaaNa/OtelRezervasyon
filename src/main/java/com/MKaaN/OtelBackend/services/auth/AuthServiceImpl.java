package com.MKaaN.OtelBackend.services.auth;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
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

    // Admin hesabı oluşturma işlemi
    @PostConstruct
    public void createAnAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
            System.out.println("Admin Hesabı Oluşturuldu");
        } else {
            System.out.println("Admin Hesabı Mevcut");
        }
    }

    // Kullanıcı kaydını yapma işlemi
    @Override
    @Transactional // Bu işlem bir transaction içinde yapılır
    public void registerUser(User user) {
        // Şifreyi bcrypt ile şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Varsayılan olarak CUSTOMER rolü ata
        if (user.getUserRole() == null) {
            user.setUserRole(UserRole.CUSTOMER); // Kullanıcı rolü belirtilmediyse CUSTOMER olarak ata
        }

        userRepository.save(user); // Veritabanına kaydet
    }

    // Kullanıcıyı login etme işlemi
    @Override
    public String loginUser(String email, String password) {
        User user = getUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Geçersiz şifre");
        }

        return generateToken(user); // JWT token üret
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    // Kullanıcı şifresini kontrol et
    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // JWT token üretme işlemi
    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getUserRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 saatlik geçerlilik süresi
                .signWith(SignatureAlgorithm.HS256, "secret_key")
                .compact();
    }
}