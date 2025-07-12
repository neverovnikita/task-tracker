package com.neverov.tasktracker.controller;

import com.neverov.tasktracker.controller.dto.request.user.UserCreateDto;
import com.neverov.tasktracker.controller.dto.request.user.UserPutDto;
import com.neverov.tasktracker.controller.dto.response.UserResponseDto;
import com.neverov.tasktracker.entity.User;
import com.neverov.tasktracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID id) {
        Optional<User> userOpt = userService.getUserById(id);
        return userOpt.map(user -> ResponseEntity.ok(new UserResponseDto(user))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto dto) {
        return ResponseEntity.ok(new UserResponseDto(userService.createUser(new User(dto.getUsername(), dto.getEmail()))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserPutDto dto) {
        return ResponseEntity.ok(new UserResponseDto(userService.updateUser(id, dto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> partialUpdateUser(
            @PathVariable UUID id, @Valid @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(new UserResponseDto(userService.partialUpdateUser(id, updates)));
    }

}
