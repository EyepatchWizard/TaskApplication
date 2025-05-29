package com.sazzad.task.domain.dto;

import com.sazzad.task.domain.entities.TaskPriority;
import com.sazzad.task.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}
