package com.neverov.tasktracker.entity;

import com.neverov.tasktracker.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username обязательно")
    @Size(min = 5, max = 25, message = "Username должен быть 5 - 25 символов")
    private String username;

    @Setter
    @Column(nullable = false, unique = true)
    @Email(message = "Некорректный email")
    private String email;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
