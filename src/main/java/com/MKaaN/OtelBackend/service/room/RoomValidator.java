package com.MKaaN.OtelBackend.service.room;

import com.MKaaN.OtelBackend.constant.Constants;
import com.MKaaN.OtelBackend.dto.request.RoomCreateRequest;
import com.MKaaN.OtelBackend.dto.request.RoomUpdateRequest;
import com.MKaaN.OtelBackend.exception.BadRequestException;
import com.MKaaN.OtelBackend.exception.RoomNotFoundException;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RoomValidator {
    private final RoomRepository roomRepository;

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

    public void validateCreateRequest(RoomCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Room create request cannot be null");
        }
        if (request.getNumber() == null || request.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (request.getType() == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        if (request.getPrice() == null || request.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Room price must be greater than zero");
        }
        if (request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
    }

    public void validateUpdateRequest(String id, RoomUpdateRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty");
        }
        if (request == null) {
            throw new IllegalArgumentException("Room update request cannot be null");
        }
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("Room not found with id: " + id);
        }
        if (request.getNumber() != null && request.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (request.getPrice() != null && request.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Room price must be greater than zero");
        }
        if (request.getCapacity() != null && request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
    }

    public void validateDeleteRequest(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty");
        }
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("Room not found with id: " + id);
        }
    }
} 