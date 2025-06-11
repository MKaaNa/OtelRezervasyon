package com.MKaaN.OtelBackend.dto.response;

import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kullanıcı bilgilerini görüntülemek için kullanılan DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private boolean active;
    private boolean verified;

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * User entity'sinden UserResponse'a dönüşüm yapar
     *
     * @param user User entity
     * @return UserResponse
     */
    public static UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setVerified(user.isVerified());
        return response;
    }
}
