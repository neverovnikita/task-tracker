package com.neverov.tasktracker.service;

import com.neverov.tasktracker.controller.dto.request.user.UserPutDto;
import com.neverov.tasktracker.entity.User;
import com.neverov.tasktracker.enums.UserRole;
import com.neverov.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User updateUser(UUID id, UserPutDto dto) {
        User existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user with id : " + id));
        existingUser.setEmail(dto.getEmail());
        existingUser.setUsername(dto.getUsername());
        existingUser.setRole(dto.getRole());

        return userRepository.save(existingUser);
    }

    public User partialUpdateUser(UUID id, Map<String, Object> updates) {
        User existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user with id : " + id));
        for (var entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "email":
                    existingUser.setEmail((String) entry.getValue());
                    break;
                case "username":
                    existingUser.setUsername((String)entry.getValue());
                    break;
                case "role":
                    boolean flag = false;
                    for (var e : UserRole.values()) {
                        if (e.name().equals(entry.getValue())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        throw new RuntimeException("Invalid user role");
                    }
                    existingUser.setRole(UserRole.valueOf((String) entry.getValue()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + entry.getKey());
            }
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
