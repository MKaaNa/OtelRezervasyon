package com.MKaaN.OtelBackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;  // UserRole enum sınıfını import et

import java.util.HashMap;

@RestController
public class UserController {

    // Bu endpoint için CORS izin veriyoruz
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Kullanıcıyı kaydetmeden önce user_role'ü 'CUSTOMER' olarak ayarlıyoruz
        user.setUserRole(UserRole.CUSTOMER);  // Her yeni kullanıcıya 'CUSTOMER' rolü verilir

        // Kullanıcıyı kaydetme işlemi
        // userService.save(user);  // Örnek bir servis çağrısı (bu kısımdaki implementasyonu kullanıyorsanız eklemeniz gerekebilir)

        // Başarı mesajını JSON olarak döndürüyoruz
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("message", "User registered successfully");
        }});
    }
}