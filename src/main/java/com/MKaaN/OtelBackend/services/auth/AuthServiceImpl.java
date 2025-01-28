package com.MKaaN.OtelBackend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthServiceImpl extends AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCrypt yerine PasswordEncoder kullanımı önerilir

    // Kullanıcıyı email ile alıyoruz
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        return optionalUser.orElse(null); // Eğer kullanıcı yoksa null döner
    }

    // Şifre doğrulama işlemi
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword); // BCrypt ile şifre karşılaştırma
    }

    // Kullanıcı kaydetme işlemi
    public void registerUser(User user) {
        // Şifreyi hashliyoruz
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Hashlenmiş şifreyi kullanıcıya set ediyoruz
        userRepository.save(user); // Kullanıcıyı kaydediyoruz
    }
}