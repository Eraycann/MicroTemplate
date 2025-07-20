package org.kafka.userservice.controller;

import jakarta.validation.Valid;
import org.kafka.userservice.dto.request.LoginRequest;
import org.kafka.userservice.dto.request.RegisterRequest;
import org.kafka.userservice.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
public interface IUserController {

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request);
}
