package com.mballem.demoparkapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de criação de usuário
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserRequestDTO {

    /**
     * Nome de usuário
     */
    private String username;

    /**
     * Senha
     */
    private String password;
}
