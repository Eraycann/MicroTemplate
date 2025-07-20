package org.kafka.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.kafka.userservice.dto.request.LoginRequest;
import org.kafka.userservice.dto.request.RegisterRequest;
import org.kafka.userservice.dto.response.AuthResponse;
import org.kafka.userservice.entity.Role;
import org.kafka.userservice.entity.User;
import org.kafka.userservice.repository.RoleRepository;
import org.kafka.userservice.repository.UserRepository;
import org.kafka.userservice.security.JwtService;
import org.kafka.userservice.service.IUserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Kullanıcı iş mantığını barındırır.
 * - Yeni kullanıcı oluşturur.
 * - Kullanıcı doğrulaması yapar.
 * - Token üretir.
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Bu email ile kayıtlı kullanıcı zaten mevcut.");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER bulunamadı."));

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .roles(Collections.singleton(role))
                .build();

        userRepository.save(user);

        // Artık token'a userId ekleniyor
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email veya şifre hatalı"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email veya şifre hatalı");
        }

        // Token'da sub = userId
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .token(token)
                .build();
    }
}
