package com.MKaaN.OtelBackend.entity;

import com.MKaaN.OtelBackend.enums.UserDTO;
import com.MKaaN.OtelBackend.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String name;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // Default constructor, userRole CUSTOMER olarak atanır
    public User() {
        this.userRole = UserRole.CUSTOMER;  // Yeni kullanıcılar için role otomatik olarak CUSTOMER atanır
    }

    // Constructor with parameters for creating a user
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = UserRole.CUSTOMER;  // Kayıt olan kullanıcıya otomatik olarak CUSTOMER rolü verilir
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return email;  // Email'i döndürüyoruz
    }

    @Override
    public String getPassword() {
        return password;  // Şifreyi döndürüyoruz
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;  // Kullanıcı her zaman aktif kabul edilir
    }

    // Kullanıcıyı oluşturma işlemi
    public static User createUser(String email, String password, String name) {
        return new User(email, password, name);
    }

    // Kullanıcının rolünü değiştirme işlemi
    public void changeUserRole(UserRole newRole) {
        this.userRole = newRole;
    }

    // Kullanıcıyı bir DTO'ya dönüştürme (örneğin, frontend için)
    public UserDTO toDTO() {
        return new UserDTO(this.email, this.name, this.userRole);
    }
}