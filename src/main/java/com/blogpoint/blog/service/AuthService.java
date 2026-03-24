package com.blogpoint.blog.service;

import com.blogpoint.blog.dto.AuthResponse;
import com.blogpoint.blog.dto.LoginRequest;
import com.blogpoint.blog.dto.RegisterRequest;
import com.blogpoint.blog.dto.UserResponse;
import jakarta.validation.Valid;

public interface AuthService {


    void register(@Valid RegisterRequest registerRequest);

    AuthResponse login(@Valid LoginRequest loginRequest);

    AuthResponse refreshToken(String refreshToken);

    UserResponse getCurrentUser();
}
