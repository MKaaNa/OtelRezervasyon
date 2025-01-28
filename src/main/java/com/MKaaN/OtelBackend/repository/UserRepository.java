package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        // Email ile kullanıcıyı bulma
        User findByEmail(String email);
}