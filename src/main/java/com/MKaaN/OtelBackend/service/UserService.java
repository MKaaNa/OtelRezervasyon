package com.MKaaN.OtelBackend.service;

import java.util.List;

import com.MKaaN.OtelBackend.dto.UserDTO;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(String id, UserDTO userDTO);
    void delete(String id);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UserDTO getUserByEmail(String email);
    void updateVerificationStatus(String id, boolean verified);
    UserDTO convertToDTO(User user);
    User convertToEntity(UserDTO userDTO);
    User getCurrentUser();
    UserDTO updateUserRole(String id, UserRole role);
} 