package com.neverov.tasktracker.controller.dto.request;

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
    private String title;
    private String description;
    private UUID authorId;
    private UUID assigneeId;
}
