package com.MKaaN.OtelBackend.services.auth;

import com.MKaaN.OtelBackend.entity.User;

public interface AuthService {

    void registerUser(User user);  // Kullanıcıyı kaydet

    String loginUser(String email, String password);  // Giriş için email ve şifre kontrolü

    User getUserByEmail(String email);  // Email'e göre kullanıcıyı al
}


