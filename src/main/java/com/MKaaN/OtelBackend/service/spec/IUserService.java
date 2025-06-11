package com.MKaaN.OtelBackend.service.spec;

import java.util.List;

import com.MKaaN.OtelBackend.dto.request.UserCreateRequest;
import com.MKaaN.OtelBackend.dto.request.UserUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.UserResponse;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;

public interface IUserService {
    // Temel CRUD işlemleri
    UserResponse createUser(UserCreateRequest request);
    UserResponse updateUser(String id, UserUpdateRequest request);
    void deleteUser(String id);
    UserResponse getUserById(String id);
    List<UserResponse> getAllUsers();
    UserResponse getUserByEmail(String email);

    // Kullanıcı durumu işlemleri
    UserResponse verifyUser(String id);
    UserResponse activateUser(String id);
    UserResponse deactivateUser(String id);
    UserResponse updateVerificationStatus(String id, boolean verified);

    // Rol işlemleri
    UserResponse updateUserRole(String id, UserRole role);

    // Mevcut kullanıcı işlemleri
    User getCurrentUser();
}
