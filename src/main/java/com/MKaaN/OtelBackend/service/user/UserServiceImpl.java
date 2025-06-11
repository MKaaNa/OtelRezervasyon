package com.MKaaN.OtelBackend.service.user;

import com.MKaaN.OtelBackend.dto.request.UserCreateRequest;
import com.MKaaN.OtelBackend.dto.request.UserUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.UserResponse;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.mapper.UserMapper;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.service.spec.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(String id) {
        return userMapper.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        
        userMapper.updateUser(request, user);
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email)));
    }

    @Override
    public UserResponse verifyUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setVerified(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setActive(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setActive(false);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateVerificationStatus(String id, boolean verified) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setVerified(verified);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserRole(String id, UserRole role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setRole(role);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
} 