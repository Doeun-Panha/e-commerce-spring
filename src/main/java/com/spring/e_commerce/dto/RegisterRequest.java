package com.spring.e_commerce.dto;

import com.spring.e_commerce.model.Role;

public record RegisterRequest(
        String username,
        String password,
        Role role // USER or ADMIN
) {}