package com.mballem.demoparkapi.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mballem.demoparkapi.web.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
