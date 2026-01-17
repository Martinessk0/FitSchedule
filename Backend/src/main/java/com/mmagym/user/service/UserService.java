package com.mmagym.user.service;

import com.mmagym.user.dto.request.UserCreateRequest;
import com.mmagym.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create (UserCreateRequest request);

    UserResponse getById (Long id);

    List<UserResponse> getAllUsers();
}
