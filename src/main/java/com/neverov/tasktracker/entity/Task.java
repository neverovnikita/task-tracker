package com.neverov.tasktracker.entity;

import com.neverov.tasktracker.enums.TaskPriority;
import com.neverov.tasktracker.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NotBlank(message = "Title не может быть пустым")
    @Column(nullable = false, length = 100)
    private String title;

    @Setter
    private String description;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    @NonNull
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    @Setter
    private User assignee;

    private final Instant createdAt = Instant.now();

    @Setter
    private Instant updatedAt = Instant.now();

    public Task(String title, String description, TaskPriority priority, @NonNull User author, @NonNull User assignee) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NEW;
        setPriority(priority);
        this.author = author;
        this.assignee = assignee;
    }
}
