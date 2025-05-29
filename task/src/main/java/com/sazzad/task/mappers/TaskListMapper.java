package com.sazzad.task.mappers;

import com.sazzad.task.domain.dto.TaskListDto;
import com.sazzad.task.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
