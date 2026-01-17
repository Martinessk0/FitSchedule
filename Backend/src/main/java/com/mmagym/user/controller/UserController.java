package com.mmagym.user.controller;

import com.mmagym.user.dto.request.UserCreateRequest;
import com.mmagym.user.dto.response.UserResponse;
import com.mmagym.user.repository.UserRepository;
import com.mmagym.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create (
            @RequestBody(required = true)
            @Valid UserCreateRequest request
    ) {
        UserResponse response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public UserResponse getById (@PathVariable Long id) {return userService.getById(id);}

    @GetMapping
    public List<UserResponse> getAllUsers () {
        return userService.getAllUsers();
    }
}
