package com.MKaaN.OtelBackend;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserDTO;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;  // Import PasswordEncoder


import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder'ı injekt ediyoruz

    // Kullanıcıyı oluşturmak için API endpointi (POST methodu)
    @PostMapping("/user-info")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userRequest) {
        // User sınıfından yeni bir kullanıcı oluşturuluyor
        User user = new User(userRequest.getEmail(), userRequest.getPassword(), userRequest.getName());
        // Burada kullanıcının rolünü değiştirme veya başka işlemler yapılabilir.
        user.setUserRole(UserRole.ADMIN); // Örnek olarak rolü ADMIN olarak değiştiriyoruz
        userRepository.save(user);  // Kullanıcıyı veritabanına kaydediyoruz

        // DTO'ya dönüştürülüyor
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getUserRole());
        return ResponseEntity.ok(userDTO);
    }

    // Backend: UserController.java

    // Yeni kullanıcı eklemek için API endpointi
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Şifreyi hash'liyoruz
        userRepository.save(user);  // Kullanıcıyı veritabanına kaydediyoruz
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Kullanıcıyı silmek için API endpointi (DELETE methodu)
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.noContent().build();  // Kullanıcı silindi
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Kullanıcı bulunamadı
        }
    }

    // Kullanıcıyı güncelleme endpoint'i
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setName(updatedUser.getName());
            user.setUserRole(updatedUser.getUserRole());
            // Şifreyi güncellemeyin, çünkü şifreyi istemci tarafında değiştirmelisiniz
            userRepository.save(user); // Kullanıcıyı güncelliyoruz
            return ResponseEntity.ok(user.toDTO()); // Güncellenmiş kullanıcıyı DTO olarak döndürüyoruz
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Kullanıcı bilgilerini almak için API endpointi (GET methodu)
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();  // Tüm kullanıcıları alıyoruz
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getId(),user.getEmail(), user.getName(), user.getUserRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);  // Tüm kullanıcıları döndürüyoruz
    }

}