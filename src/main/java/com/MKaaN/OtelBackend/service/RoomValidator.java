package com.MKaaN.OtelBackend.service;

import com.MKaaN.OtelBackend.constant.Constants;
import com.MKaaN.OtelBackend.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class RoomValidator {


    public void validateEmail(String email) {
        if (email == null || !Pattern.matches(Constants.Validation.EMAIL_PATTERN, email)) {
            throw new BadRequestException("Geçersiz email formatı");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.length() < Constants.Validation.MIN_PASSWORD_LENGTH) {
            throw new BadRequestException("Şifre en az " + Constants.Validation.MIN_PASSWORD_LENGTH + " karakter olmalıdır");
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !Pattern.matches(Constants.Validation.PHONE_PATTERN, phoneNumber)) {
            throw new BadRequestException("Geçersiz telefon numarası formatı");
        }
    }

    public void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new BadRequestException("Başlangıç ve bitiş tarihleri gereklidir");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Başlangıç tarihi bugünden önce olamaz");
        }

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("Bitiş tarihi başlangıç tarihinden önce olamaz");
        }
    }

    public void validatePrice(double price) {
        if (price <= 0) {
            throw new BadRequestException("Fiyat sıfırdan büyük olmalıdır");
        }
    }
} 