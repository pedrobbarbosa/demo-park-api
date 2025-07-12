package com.mballem.demoparkapi.web.dto;

import com.mballem.demoparkapi.web.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String username;
    private User.Role role;
}
