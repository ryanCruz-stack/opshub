package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service 
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request){
        
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> 
                new UserNotFoundException("User Not Found")
            );
    }

    public User updateUser(Long id, CreateUserRequest request) {
         User user = userRepository.findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException("User Not Found")
            );

            if (request.getName() != null) {
                user.setName(request.getName());
            }

            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }

            return userRepository.save(user);
        }
    
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException("User Not Found")
                );
        
        userRepository.delete(user);

        return "User delete Successfully";
    }
}
