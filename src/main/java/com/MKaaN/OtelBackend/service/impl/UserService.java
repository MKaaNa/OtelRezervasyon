package com.MKaaN.OtelBackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MKaaN.OtelBackend.dto.UserDTO;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.UserRole;
import com.MKaaN.OtelBackend.exception.BadRequestException;
import com.MKaaN.OtelBackend.exception.NotFoundException;
import com.MKaaN.OtelBackend.mapper.UserMapper;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.service.spec.IUserService;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        // E-posta kontrolü
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Bu e-posta adresi zaten kullanılıyor");
        }

        // Kullanıcı oluşturma
        User user = createUserEntity(userDTO);
        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    private User createUserEntity(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true);
        user.setVerified(false);
        return user;
    }

    @Override
    @Transactional
    public UserDTO updateUser(String id, UserDTO userDTO) {
        // Kullanıcıyı bul
        User existingUser = findUserById(id);

        // Kullanıcıyı güncelle
        UserMapper.updateEntityFromDTO(userDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return UserMapper.toDTO(updatedUser);
    }

    private User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı"));
    }

    @Override
    @Transactional
    public void delete(String id) {
        // Kullanıcının varlığını kontrol et
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        // Kullanıcıyı sil
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = findUserById(id);
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = findUserByEmail(email);
        return UserMapper.toDTO(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı"));
    }

    @Override
    @Transactional
    public void updateVerificationStatus(String id, boolean verified) {
        // Kullanıcıyı bul
        User user = findUserById(id);

        // Doğrulama durumunu güncelle
        user.setVerified(verified);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO updateUserRole(String id, UserRole role) {
        // Kullanıcıyı bul
        User user = findUserById(id);

        // Rolü güncelle
        user.setRole(role);
        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return findUserByEmail(email);
        }

        throw new NotFoundException("Oturum açmış kullanıcı bulunamadı");
    }
}
