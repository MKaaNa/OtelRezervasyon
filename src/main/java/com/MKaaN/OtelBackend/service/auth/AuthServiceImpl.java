package com.MKaaN.OtelBackend.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MKaaN.OtelBackend.dto.response.UserResponse;
import com.MKaaN.OtelBackend.dto.request.LoginRequest;
import com.MKaaN.OtelBackend.dto.request.RegisterRequest;
import com.MKaaN.OtelBackend.dto.request.UserCreateRequest;
import com.MKaaN.OtelBackend.dto.response.JwtResponse;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.mapper.UserMapper;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.security.JwtTokenProvider;
import com.MKaaN.OtelBackend.service.spec.AuthService;
import com.MKaaN.OtelBackend.service.spec.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserService userService;
    private final UserMapper userMapper;

    @Override
    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .active(true)
                .verified(false)
                .build();

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(jwt);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(jwt);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void registerUser(UserResponse userResponse) {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .username(userResponse.getUsername())
                .email(userResponse.getEmail())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .phoneNumber(userResponse.getPhoneNumber())
                .role(userResponse.getRole())
                .active(userResponse.isActive())
                .verified(userResponse.isVerified())
                .build();
        userService.createUser(userCreateRequest);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + email));
        return userMapper.toResponse(user);
    }

    @Override
    public JwtResponse authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(token);
    }
}
