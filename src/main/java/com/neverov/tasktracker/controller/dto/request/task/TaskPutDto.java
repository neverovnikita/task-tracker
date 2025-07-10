package com.neverov.tasktracker.controller.dto.request.task;

import com.neverov.tasktracker.enums.TaskPriority;
import com.neverov.tasktracker.enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
public class TaskPutDto {
    @NotBlank(message = "Title не может быть пустым")
    @Size(max = 100)
    private String title;


    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private UUID assigneeId;
}
