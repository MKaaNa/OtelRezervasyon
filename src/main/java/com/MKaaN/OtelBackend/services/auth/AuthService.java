package com.MKaaN.OtelBackend.services.auth;

import com.MKaaN.OtelBackend.entity.User;

public interface AuthService {
    void registerUser(User user);
    String loginUser(String email, String password);
    User getUserByEmail(String email);
    boolean checkPassword(String rawPassword, String encodedPassword);
}