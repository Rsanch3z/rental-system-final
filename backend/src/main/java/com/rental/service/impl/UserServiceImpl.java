package com.rental.service.impl;

import com.rental.model.User;
import com.rental.repository.UserRepository;
import com.rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // ✅ 加上這段：
    @Override
    public User register(User user) {
        // 可加入驗重或其他邏輯
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // 密碼加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 儲存
        return userRepository.save(user);
    }
}
