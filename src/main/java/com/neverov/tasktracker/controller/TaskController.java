package com.neverov.tasktracker.controller;

import com.neverov.tasktracker.controller.dto.TaskCreateDto;
import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.entity.User;
import com.neverov.tasktracker.enums.TaskPriority;
import com.neverov.tasktracker.service.TaskService;
import com.neverov.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskCreateDto dto) {
        User author = userService.getUserById(dto.getAuthorId());
        User assignee = userService.getUserById(dto.getAssigneeId());
        Task task = new Task(dto.getTitle(), dto.getDescription(), TaskPriority.UNKNOWN, author, assignee);
        return taskService.createTask(task);
    }

    @GetMapping("author/{id}")
    public List<Task> getTaskByAuthorId(@PathVariable UUID id) {
        return taskService.getAllTasksByAuthorId(id);
    }

    @GetMapping("assignee/{id}")
    public List<Task> getTaskByAssigneeId(@PathVariable UUID id) {
        return taskService.getAllTasksByAssigneeId(id);
    }

}
