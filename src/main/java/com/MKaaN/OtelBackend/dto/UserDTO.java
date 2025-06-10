package com.MKaaN.OtelBackend.dto;

import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private boolean active;
    private boolean verified;
    private String verificationToken;
    private String resetToken;
    private LocalDateTime resetTokenExpiry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .active(user.isActive())
                .verified(user.isVerified())
                .verificationToken(user.getVerificationToken())
                .resetToken(user.getResetToken())
                .resetTokenExpiry(user.getResetTokenExpiry())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phoneNumber(this.phoneNumber)
                .role(this.role)
                .active(this.active)
                .verified(this.verified)
                .verificationToken(this.verificationToken)
                .resetToken(this.resetToken)
                .resetTokenExpiry(this.resetTokenExpiry)
                .build();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }
    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public static class UserDTOBuilder {
        private String id;
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private UserRole role;
        private boolean active;
        private boolean verified;
        private String verificationToken;
        private String resetToken;
        private LocalDateTime resetTokenExpiry;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public UserDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDTOBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public UserDTOBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserDTOBuilder verified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public UserDTOBuilder verificationToken(String verificationToken) {
            this.verificationToken = verificationToken;
            return this;
        }

        public UserDTOBuilder resetToken(String resetToken) {
            this.resetToken = resetToken;
            return this;
        }

        public UserDTOBuilder resetTokenExpiry(LocalDateTime resetTokenExpiry) {
            this.resetTokenExpiry = resetTokenExpiry;
            return this;
        }

        public UserDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDTOBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserDTO build() {
            UserDTO dto = new UserDTO();
            dto.setId(id);
            dto.setUsername(username);
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setPhoneNumber(phoneNumber);
            dto.setRole(role);
            dto.setActive(active);
            dto.setVerified(verified);
            dto.setVerificationToken(verificationToken);
            dto.setResetToken(resetToken);
            dto.setResetTokenExpiry(resetTokenExpiry);
            dto.setCreatedAt(createdAt);
            dto.setUpdatedAt(updatedAt);
            return dto;
        }
    }
} 