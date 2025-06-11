package com.MKaaN.OtelBackend.service.spec;

import com.MKaaN.OtelBackend.dto.response.UserResponse;
import com.MKaaN.OtelBackend.dto.request.LoginRequest;
import com.MKaaN.OtelBackend.dto.request.RegisterRequest;
import com.MKaaN.OtelBackend.dto.response.JwtResponse;

public interface AuthService {
    /**
     * Yeni kullanıcı kaydı oluşturur
     *
     * @param request Kayıt bilgileri
     * @return JWT token ve kullanıcı bilgileri
     */
    JwtResponse register(RegisterRequest request);

    /**
     * Kullanıcı girişi yapar
     *
     * @param request Giriş bilgileri
     * @return JWT token ve kullanıcı bilgileri
     */
    JwtResponse login(LoginRequest request);

    /**
     * Kullanıcı kimlik doğrulaması yapar
     *
     * @param email Kullanıcı email
     * @param password Kullanıcı şifre
     * @return JWT token ve kullanıcı bilgileri
     */
    JwtResponse authenticate(String email, String password);

    /**
     * Verilen şifrelerin eşleşip eşleşmediğini kontrol eder
     *
     * @param rawPassword Şifre
     * @param encodedPassword Kodlanmış şifre
     * @return Eşleşiyorsa true, değilse false
     */
    boolean checkPassword(String rawPassword, String encodedPassword);

    /**
     * Kullanıcı kaydeder
     *
     * @param userDTO Kullanıcı bilgileri
     */
    void registerUser(UserResponse userDTO);

    /**
     * Email ile kullanıcı bilgilerini getirir
     *
     * @param email Kullanıcı email
     * @return Kullanıcı bilgileri
     */
    UserResponse getUserByEmail(String email);
}
