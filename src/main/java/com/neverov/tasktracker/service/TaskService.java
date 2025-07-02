package com.neverov.tasktracker.service;

import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElse(null);
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
    public Task updateTaskById(UUID id, Task task) {
        Task beforeTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        beforeTask.setTitle(task.getTitle());
        beforeTask.setDescription(task.getDescription());
        beforeTask.setPriority(task.getPriority());
        beforeTask.setStatus(task.getStatus());
        beforeTask.setUpdatedAt(Instant.now());
        beforeTask.setAssignee(task.getAssignee());
        return taskRepository.save(beforeTask);
    }
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
}
