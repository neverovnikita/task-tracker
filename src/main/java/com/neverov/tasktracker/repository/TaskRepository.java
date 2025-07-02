package com.neverov.tasktracker.repository;

import com.neverov.tasktracker.entity.Task;
import com.neverov.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByAuthorId(UUID authorId);

    List<Task> findAllByAssignee(User assignee);

    List<Task> findAllByAssigneeId(UUID assigneeId);
}

