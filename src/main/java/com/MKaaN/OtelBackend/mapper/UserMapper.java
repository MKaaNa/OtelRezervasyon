package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.UserDTO;
import com.MKaaN.OtelBackend.entity.User;

public class UserMapper {
    
    private UserMapper() {
        // Private constructor
    }

    public static UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .role(entity.getRole())
                .active(entity.isActive())
                .verified(entity.isVerified())
                .verificationToken(entity.getVerificationToken())
                .resetToken(entity.getResetToken())
                .resetTokenExpiry(entity.getResetTokenExpiry())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .active(dto.isActive())
                .verified(dto.isVerified())
                .verificationToken(dto.getVerificationToken())
                .resetToken(dto.getResetToken())
                .resetTokenExpiry(dto.getResetTokenExpiry())
                .build();
    }

    public static void updateEntityFromDTO(UserDTO dto, User entity) {
        if (entity == null || dto == null) {
            return;
        }
        
        if (dto.getUsername() != null) {
            entity.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getPhoneNumber() != null) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }

        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }

        entity.setActive(dto.isActive());
        entity.setVerified(dto.isVerified());

        if (dto.getVerificationToken() != null) {
            entity.setVerificationToken(dto.getVerificationToken());
        }

        if (dto.getResetToken() != null) {
            entity.setResetToken(dto.getResetToken());
        }

        if (dto.getResetTokenExpiry() != null) {
            entity.setResetTokenExpiry(dto.getResetTokenExpiry());
        }
    }
}
