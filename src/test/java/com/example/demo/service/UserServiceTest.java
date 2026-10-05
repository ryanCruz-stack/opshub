package com.example.demo.service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;

class UserServiceTest {

    @Test
    void createUserCreatesUser() {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);

        UserService userService = new UserService(userRepository);

        CreateUserRequest request = new CreateUserRequest("Alex", "alex@example.com", "hello123");

        User savedUser = new User();
        savedUser.setName("Alex");
        savedUser.setEmail("alex@example.com");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // Act
        User result = userService.createUser(request);

        // Assert
        assertEquals("Alex", result.getName());
        assertEquals("alex@example.com", result.getEmail());

        //Interaction Verificatio
        verify(userRepository).save(argThat(user -> 
            user.getName().equals("Alex") &&
            user.getEmail().equals("alex@example.com")
        ));
    }
}