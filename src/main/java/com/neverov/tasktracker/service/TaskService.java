package com.neverov.tasktracker.service;

import com.neverov.tasktracker.controller.dto.request.task.TaskCreateDto;
import com.neverov.tasktracker.controller.dto.request.task.TaskPutDto;
import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.entity.User;
import com.neverov.tasktracker.enums.TaskPriority;
import com.neverov.tasktracker.enums.TaskStatus;
import com.neverov.tasktracker.repository.TaskRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    public void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasksByAuthorId(UUID authorId) {
        return taskRepository.findAllByAuthorId(authorId);
    }

    public List<Task> getAllTasksByAssigneeId(UUID assigneeId) {
        return taskRepository.findAllByAssigneeId(assigneeId);
    }

    public Task updateTaskById(UUID id, TaskPutDto dto) {
        Task beforeTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        Optional<User> assigneeOpt = userService.getUserById(dto.getAssigneeId());

        beforeTask.setTitle(dto.getTitle());
        beforeTask.setDescription(dto.getDescription());
        beforeTask.setPriority(dto.getPriority());
        beforeTask.setStatus(dto.getStatus());
        beforeTask.setUpdatedAt(Instant.now());
        if (assigneeOpt.isPresent()) {
            beforeTask.setAssignee(assigneeOpt.get());
        } else throw new RuntimeException("Assignee not found with id: " + dto.getAssigneeId());

        return taskRepository.save(beforeTask);
    }

    public Task createTask(TaskCreateDto dto) {
        Optional<User> authorOpt = userService.getUserById(dto.getAuthorId());
        User author;
        if (authorOpt.isPresent()) {
            author = authorOpt.get();
        } else throw new RuntimeException("Author not found with id: " + dto.getAuthorId());

        Optional<User> assigneeOpt = userService.getUserById(dto.getAssigneeId());
        User assignee;
        if (assigneeOpt.isPresent()) {
            assignee = assigneeOpt.get();
        } else throw new RuntimeException("Author not found with id: " + dto.getAssigneeId());

        Task task = new Task(dto.getTitle(), dto.getDescription(), TaskPriority.UNKNOWN, author, assignee);
        return taskRepository.save(task);
    }

    public Task partialUpdateTask(UUID id, @NotNull Map<String, Object> updates) {
        Optional<Task> beforeTaskOpt = getTaskById(id);
        Task beforeTask;
        if (beforeTaskOpt.isPresent()) {
            beforeTask = beforeTaskOpt.get();
        } else throw new RuntimeException("Task not found with id: " + id);

        for (var entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "title": {
                    beforeTask.setTitle(entry.getValue().toString());
                    break;
                }
                case "description": {
                    beforeTask.setDescription(entry.getValue().toString());
                    break;
                }
                case "priority": {
                    try {
                        TaskPriority priority = TaskPriority.valueOf((String) entry.getValue());
                        beforeTask.setPriority(priority);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid task priority: " + entry.getValue());
                    }
                    break;
                }
                case "assigneeId": {
                    Optional<User> assigneeOpt = userService.getUserById(UUID.fromString(entry.getValue().toString()));
                    if (assigneeOpt.isPresent()) {
                        beforeTask.setAssignee(assigneeOpt.get());
                    } else throw new RuntimeException("Assignee not found with id: " + entry.getValue());
                    break;
                }
                case "status": {
                    try {
                        TaskStatus status = TaskStatus.valueOf((String) entry.getValue());
                        beforeTask.setStatus(status);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid task status: " + entry.getValue());
                    }
                    break;
                }
                default: {
                    throw new RuntimeException("Invalid key: " + entry.getKey());
                }
            }

        }
        beforeTask.setUpdatedAt(Instant.now());
        return taskRepository.save(beforeTask);
    }
}
