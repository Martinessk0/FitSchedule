package com.mmagym.user.service;

import com.mmagym.common.exception.BadRequestException;
import com.mmagym.common.exception.ConflictException;
import com.mmagym.common.exception.NotFoundException;
import com.mmagym.user.User;
import com.mmagym.user.dto.request.UserCreateRequest;
import com.mmagym.user.dto.response.UserResponse;
import com.mmagym.user.mapper.UserMapper;
import com.mmagym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {

        if (request == null) throw new BadRequestException("Request body is required");

        if (request.getEmail() == null) throw new BadRequestException("Email is required");

        if (request.getFirstName() == null) throw new BadRequestException("First name  is required");

        if (request.getLastName() == null) throw new BadRequestException("Last name  is required");

        if (userRepository.existsByEmail(request.getEmail())) throw new ConflictException("Email already exists");

        User user = UserMapper.toEntity(request);

        User saved = userRepository.save(user);

        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        if (id == null) throw new BadRequestException("id is required");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id" + id + "not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
