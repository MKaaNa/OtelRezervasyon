package com.MKaaN.OtelBackend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MKaaN.OtelBackend.dto.ApiResponse;
import com.MKaaN.OtelBackend.dto.UserDTO;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.service.spec.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserDTO> add(@Valid @RequestBody UserDTO userDTO) {
        return ApiResponse.success(userService.createUser(userDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserDTO> edit(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        return ApiResponse.success(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> del(@PathVariable String id) {
        userService.delete(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserDTO> get(@PathVariable String id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserDTO>> getAll() {
        return ApiResponse.success(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserDTO> getByEmail(@PathVariable String email) {
        return ApiResponse.success(userService.getUserByEmail(email));
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> getCurrent() {
        return ApiResponse.success(userService.getUserByEmail(userService.getCurrentUser().getEmail()));
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateVerify(@PathVariable String id, @RequestParam boolean verified) {
        userService.updateVerificationStatus(id, verified);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/role")
    public ApiResponse<UserDTO> updateRole(@PathVariable String id, @RequestParam UserRole role) {
        return ApiResponse.success(userService.updateUserRole(id, role));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserDTO> addAdmin(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setRole(UserRole.ADMIN);
        return ApiResponse.success(userService.createUser(userDTO));
    }
}
