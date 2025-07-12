package com.neverov.tasktracker.controller;

import com.neverov.tasktracker.controller.dto.request.task.TaskCreateDto;
import com.neverov.tasktracker.controller.dto.request.task.TaskPutDto;
import com.neverov.tasktracker.controller.dto.response.TaskResponseDto;
import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.service.TaskService;
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
import java.util.List;
import java.util.Map;
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
        return taskOpt.map(task -> ResponseEntity.ok(new TaskResponseDto(task))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateDto dto) {
        return ResponseEntity.ok(new TaskResponseDto(taskService.createTask(dto)));
    }

    @GetMapping("author/{id}")
    public ResponseEntity<List<TaskResponseDto>> getTaskByAuthorId(@PathVariable UUID id) {
        List<Task> tasks = taskService.getAllTasksByAuthorId(id);
        List<TaskResponseDto> taskResponseDtos = tasks.stream().map(TaskResponseDto::new).toList();
        return ResponseEntity.ok(taskResponseDtos);
    }

    @GetMapping("assignee/{id}")
    public ResponseEntity<List<TaskResponseDto>> getTaskByAssigneeId(@PathVariable UUID id) {
        List<Task> tasks = taskService.getAllTasksByAssigneeId(id);
        List<TaskResponseDto> taskResponseDtos = tasks.stream().map(TaskResponseDto::new).toList();
        return ResponseEntity.ok(taskResponseDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable UUID id, @RequestBody TaskPutDto dto) {
        return ResponseEntity.ok(new TaskResponseDto(taskService.updateTaskById(id, dto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> partialUpdateTask(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(new TaskResponseDto(taskService.partialUpdateTask(id, updates)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
