package com.mballem.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.web.dto.LoginRequestDTO;
import com.mballem.demoparkapi.web.dto.LoginResponseDTO;
import com.mballem.demoparkapi.web.entity.User;
import com.mballem.demoparkapi.web.jwt.JwtUtil;
import com.mballem.demoparkapi.web.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login request received for username: {}", loginRequest.getUsername());
        User user = userService.findByUsername(loginRequest.getUsername());

        log.info("User found: {}", user);
        log.info("Password encoder: {}", passwordEncoder);
        log.info("Password: {}", loginRequest.getPassword());
        log.info("User password: {}", user.getPassword());
        log.info("Password matches: {}", passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        List<String> roles = List.of(user.getRole().name());
        String token = JwtUtil.generateToken(user.getUsername(), roles);
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getUsername(), roles));
    }

} 