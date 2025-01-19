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
    private PasswordEncoder passwordEncoder;

    // getUserByEmail metodunu güncelliyoruz
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);  // findByEmail Optional döner
        return optionalUser.orElse(null);  // Eğer kullanıcı varsa döner, yoksa null döner
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);  // Şifreyi karşılaştırıyoruz
    }

    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());  // Şifreyi encode et
        user.setPassword(encodedPassword);  // Şifreyi encode edilen versiyonla değiştir
        userRepository.save(user);  // Kullanıcıyı kaydet
    }
}