package org.kafka.userservice.service;

import org.kafka.userservice.dto.request.LoginRequest;
import org.kafka.userservice.dto.request.RegisterRequest;
import org.kafka.userservice.dto.response.AuthResponse;

/**
 * Kullanıcı ile ilgili iş mantığını tanımlar.
 * - Register işlemi
 * - Login işlemi (JWT üretimi dahil)
 */
public interface IUserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
