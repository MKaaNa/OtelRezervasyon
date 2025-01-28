package com.MKaaN.OtelBackend.enums;

import com.MKaaN.OtelBackend.entity.User;
import lombok.Setter;

public class UserDTO {

    @Setter
    private String email;
    @Setter
    private String name;
    @Setter
    private UserRole userRole;
    private Long id;
    public UserDTO(Long id, String email, String name, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.userRole = userRole;
        this.id = id;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Long setId(Long id) {
        return id;
    }

    public Long getId()
    {return id;}

    // DTO'dan User objesine dönüştürme metodunu ekleyebiliriz
    public User toUser() {
        return new User(this.email, "", this.name);  // Şifre burada verilmediği için boş bırakıyoruz
    }
}