package com.rental.dto;

import com.rental.model.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private User user;
} 