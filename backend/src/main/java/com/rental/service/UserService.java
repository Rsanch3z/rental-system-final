package com.rental.service;

import com.rental.model.User;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User register(User user); // ← 加這行
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
