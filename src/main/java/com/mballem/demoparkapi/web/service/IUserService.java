package com.mballem.demoparkapi.web.service;

import java.util.List;

import com.mballem.demoparkapi.web.dto.UserRequestDTO;
import com.mballem.demoparkapi.web.dto.UserResponseDTO;
import com.mballem.demoparkapi.web.entity.User;

public interface IUserService {
    User save(UserRequestDTO userRequestDTO);

    UserResponseDTO findById(Long id);

    List<UserResponseDTO> findAll();

    void deleteUser(Long id);
  
}
