package com.example.demo.controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;


@RestController
public class UserController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserResponse createUser(
        @Valid @RequestBody CreateUserRequest request) {

        User user = userService.createUser(request);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers() {

        List<User> users = userService.getUsers();

        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable  Long id) {

        User user = userService.getUser(id);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @PatchMapping("/users/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody CreateUserRequest request) {

        User user = userService.updateUser(id, request);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @DeleteMapping("users/{id}")
    public String deleteUser(@PathVariable Long id) {

        return userService.deleteUser(id);
    }
}