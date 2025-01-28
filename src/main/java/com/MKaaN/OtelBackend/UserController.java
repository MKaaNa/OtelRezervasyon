package com.MKaaN.OtelBackend;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserDTO;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Kullanıcıyı oluşturmak için API endpointi (POST methodu)
    @PostMapping("/user-info")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userRequest) {
        // User sınıfından yeni bir kullanıcı oluşturuluyor
        User user = new User(userRequest.getEmail(), userRequest.getPassword(), userRequest.getName());
        // Burada kullanıcının rolünü değiştirme veya başka işlemler yapılabilir.
        user.setUserRole(UserRole.ADMIN); // Örnek olarak rolü ADMIN olarak değiştiriyoruz
        userRepository.save(user);  // Kullanıcıyı veritabanına kaydediyoruz

        // DTO'ya dönüştürülüyor
        UserDTO userDTO = new UserDTO(user.getEmail(), user.getName(), user.getUserRole());
        return ResponseEntity.ok(userDTO);
    }

    // Kullanıcı bilgilerini almak için API endpointi (GET methodu)
    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam String email) {
        // Email ile kullanıcıyı veritabanında arıyoruz
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            // Kullanıcı bulundu, DTO'ya dönüştürülüyor
            UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getName(), user.get().getUserRole());
            return ResponseEntity.ok(userDTO);
        } else {
            // Kullanıcı bulunamadı
            return ResponseEntity.status(404).body(null);
        }
    }
}