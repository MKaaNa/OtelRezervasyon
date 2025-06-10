package com.MKaaN.OtelBackend.service.auth;

import com.MKaaN.OtelBackend.dto.JwtResponse;
import com.MKaaN.OtelBackend.dto.LoginRequest;
import com.MKaaN.OtelBackend.dto.RegisterRequest;
import com.MKaaN.OtelBackend.dto.UserDTO;

public interface AuthService {
    /**
     * Kullanıcı kaydı yapar
     * 
     * @param request Kayıt isteği
     * @return JWT token ve kullanıcı bilgilerini içeren yanıt
     */
    JwtResponse register(RegisterRequest request);

    /**
     * Kullanıcı girişi yapar
     * 
     * @param request Giriş isteği
     * @return JWT token ve kullanıcı bilgilerini içeren yanıt
     */
    JwtResponse login(LoginRequest request);

    /**
     * Email ile kullanıcı kimlik doğrulaması yapar
     * 
     * @param email Kullanıcı email
     * @param password Kullanıcı şifresi
     * @return JWT token ve kullanıcı rolünü içeren yanıt
     */
    JwtResponse authenticate(String email, String password);

    /**
     * Email ile kullanıcı bilgilerini getirir
     * 
     * @param email Kullanıcı email
     * @return Kullanıcı bilgileri
     */
    UserDTO getUserByEmail(String email);

    /**
     * Yeni kullanıcı kaydeder
     * 
     * @param userDTO Kaydedilecek kullanıcı bilgileri
     */
    void registerUser(UserDTO userDTO);

    /**
     * Şifre kontrolü yapar
     * 
     * @param rawPassword Ham şifre
     * @param encodedPassword Şifrelenmiş şifre
     * @return Eşleşme durumu
     */
    boolean checkPassword(String rawPassword, String encodedPassword);
}
