package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Email ile kullanıcı bul
    Optional<User> findByEmail(String email);
    
    // Email ile kullanıcı var mı kontrol et
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
} 