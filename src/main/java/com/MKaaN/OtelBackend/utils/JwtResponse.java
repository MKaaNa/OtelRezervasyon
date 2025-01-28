package com.MKaaN.OtelBackend.utils;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    // Getter ve Setter
    @Getter
    @Setter
    private String token;
    private String userRole;

    // Constructor
    public JwtResponse(String token, String userRole) {
        this.userRole = userRole;
        this.token = token;
    }

    public String getRole() {
        return userRole;
    }

    public void setRole(String role) {
        this.userRole = role;
    }
}