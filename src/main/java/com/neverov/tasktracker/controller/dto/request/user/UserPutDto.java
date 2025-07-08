package com.neverov.tasktracker.controller.dto.request.user;

import com.neverov.tasktracker.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPutDto {
    @Size(min = 5, max = 25, message = "Username должен быть 5 - 25 символов")
    private String username;

    @Email(message = "Некорректный email")
    private String email;

    private UserRole role;
}
