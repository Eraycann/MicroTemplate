package org.kafka.userservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.kafka.userservice.controller.IUserController;
import org.kafka.userservice.dto.request.LoginRequest;
import org.kafka.userservice.dto.request.RegisterRequest;
import org.kafka.userservice.dto.response.AuthResponse;
import org.kafka.userservice.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final IUserService userService;

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
