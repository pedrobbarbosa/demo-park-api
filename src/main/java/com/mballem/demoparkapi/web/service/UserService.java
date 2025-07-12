package com.mballem.demoparkapi.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mballem.demoparkapi.web.dto.UserRequestDTO;
import com.mballem.demoparkapi.web.dto.UserResponseDTO;
import com.mballem.demoparkapi.web.entity.User;
import com.mballem.demoparkapi.web.mapper.UserMapper;
import com.mballem.demoparkapi.web.repository.UserRepository;
import com.mballem.demoparkapi.web.validation.ValidationRules;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidationRules validationRules;
    private final PasswordEncoder passwordEncoder;

    public User save(UserRequestDTO userRequestDTO) {
        User user = userMapper.toUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        validationRules.validateUser(user);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO findById(Long id) {
        log.info("UserService: Finding user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserDTO(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void deleteUser(@AuthenticationPrincipal User usuarioAutenticado,
            @RequestBody UserRequestDTO userRequestDTO) {
        User userToDelete = userMapper.toUser(userRequestDTO);
        User user = userRepository.findById(userToDelete.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        log.info("UserService: Finding user by username: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        log.info("UserService: User found: {}", user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
