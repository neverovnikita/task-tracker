package com.neverov.tasktracker.controller.dto.response;

import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.enums.TaskPriority;
import com.neverov.tasktracker.enums.TaskStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskResponseDto {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UUID authorId;
    private UUID assigneeId;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.assigneeId  = task.getAssignee().getId();
        this.authorId = task.getAuthor().getId();
    }
}
