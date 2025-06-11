package com.MKaaN.OtelBackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kullanıcı güncelleme isteği için kullanılan DTO
 * Tüm alanlar opsiyoneldir, sadece güncellenmek istenenler doldurulur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;

    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String password;

    private String firstName;
    private String lastName;

    @Pattern(regexp = "^(\\+90|0)?[0-9]{10}$", message = "Geçerli bir telefon numarası giriniz")
    private String phoneNumber;
}
