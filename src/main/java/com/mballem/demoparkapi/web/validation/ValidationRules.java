package com.mballem.demoparkapi.web.validation;

import org.springframework.stereotype.Component;
import com.mballem.demoparkapi.web.entity.User;
import com.mballem.demoparkapi.web.repository.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ValidationRules {

  private final UserRepository userRepository;

  public void validateUser(User user) {
    if (user.getUsername() == null || user.getUsername().isEmpty()) {
      throw new RuntimeException("Username is required");
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      throw new RuntimeException("Password is required");
    }
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new RuntimeException("Username already exists");
    }
  }
}
