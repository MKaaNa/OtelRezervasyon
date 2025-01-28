package com.MKaaN.OtelBackend.enums;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.repository.UserRepository;

public class UserDTO {

    private String email;
    private String name;
    private UserRole userRole;

    public UserDTO(String email, String name, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.userRole = userRole;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    // DTO'dan User objesine dönüştürme metodunu ekleyebiliriz
    public User toUser() {
        return new User(this.email, "", this.name);  // Şifre burada verilmediği için boş bırakıyoruz
    }
}