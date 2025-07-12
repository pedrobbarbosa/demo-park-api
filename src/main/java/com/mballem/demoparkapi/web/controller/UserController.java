package com.mballem.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.web.dto.UserRequestDTO;
import com.mballem.demoparkapi.web.dto.UserResponseDTO;
import com.mballem.demoparkapi.web.entity.User;
import com.mballem.demoparkapi.web.mapper.UserMapper;
import com.mballem.demoparkapi.web.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "User Controller", description = "Controller for managing users")
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

        private final UserService userService;
        private final UserMapper userMapper;

    @Operation(summary = "Create a new user",
            description = "Create a new user with the given information",
            responses = {
                @ApiResponse(responseCode = "201", description = "User created successfully",
                        content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request body",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO userRequestDTO) {

            User user = userService.save(userRequestDTO);
            UserResponseDTO userResponseDTO = userMapper.toUserDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @Operation(summary = "Get a user by id",
            description = "Get a user by id",
            responses = {
                @ApiResponse(responseCode = "200", description = "User found successfully",
                        content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "404", description = "User not found",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
                    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
            log.info("Getting user by id: {}", id);
            UserResponseDTO user = userService.findById(id);
            log.info("User found: {}", user);
            return ResponseEntity.ok(user);
    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all users",
            description = "Get all users",
            responses = {
                @ApiResponse(responseCode = "200", description = "Users found successfully",
                        content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "404", description = "Users not found",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
            List<UserResponseDTO> users = userService.findAll();
            return ResponseEntity.ok(users);
    }
}
