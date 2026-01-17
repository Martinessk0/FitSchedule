package com.mmagym.user.mapper;

import com.mmagym.user.User;
import com.mmagym.user.dto.request.UserCreateRequest;
import com.mmagym.user.dto.response.UserResponse;

public final class UserMapper {

    private UserMapper() {}

    public static User toEntity (UserCreateRequest request) {
        if (request == null) return null;

        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    public static UserResponse toResponse (User user) {
        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
