package com.neverov.tasktracker.controller;

import com.neverov.tasktracker.controller.dto.request.task.TaskCreateDto;
import com.neverov.tasktracker.controller.dto.response.TaskResponseDto;
import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        return taskOpt.isPresent() ? ResponseEntity.ok(new TaskResponseDto(taskOpt.get()))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateDto dto) {
        return ResponseEntity.ok(new TaskResponseDto(taskService.createTask(dto)));
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
