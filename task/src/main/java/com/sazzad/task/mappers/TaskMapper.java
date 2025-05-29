package com.sazzad.task.mappers;

import com.sazzad.task.domain.dto.TaskDto;
import com.sazzad.task.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
