package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Email ile kullanıcıyı bulma
}