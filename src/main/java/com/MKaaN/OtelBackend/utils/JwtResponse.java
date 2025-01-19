package com.MKaaN.OtelBackend.utils;

public class JwtResponse {
    private String token;

    // Constructor
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter ve Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}