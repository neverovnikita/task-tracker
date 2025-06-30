package com.neverov.tasktracker.service;

import com.neverov.tasktracker.entity.User;
import com.neverov.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserById(UUID id) {
        return userRepository.getById(id);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public String deleteUser(UUID id) {
        userRepository.deleteById(id);
        return "User deleted: " + id;
    }
}
