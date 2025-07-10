package com.neverov.tasktracker.controller.dto.request.task;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDto {
    @NotBlank(message = "Title не может быть пустым")
    @Size(max = 100)
    private String title;

    private String description;

    @NotNull
    private UUID authorId;


    private UUID assigneeId;
}
