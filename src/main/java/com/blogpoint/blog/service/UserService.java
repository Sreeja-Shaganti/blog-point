package com.blogpoint.blog.service;

import com.blogpoint.blog.dto.UpdateUserRequest;
import com.blogpoint.blog.dto.UserResponse;
import com.blogpoint.blog.model.User;
import com.blogpoint.blog.model.UserRole;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getCurrentUser();

    UserResponse mapToUserResponse(User currentUser);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, @Valid UpdateUserRequest request);

    void deleteUser(Long id);

    UserResponse changeUserRole(Long id, UserRole role);
}
