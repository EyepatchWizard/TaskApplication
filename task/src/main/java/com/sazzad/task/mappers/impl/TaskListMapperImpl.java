package com.sazzad.task.mappers.impl;

import com.sazzad.task.domain.dto.TaskListDto;
import com.sazzad.task.domain.entities.Task;
import com.sazzad.task.domain.entities.TaskList;
import com.sazzad.task.domain.entities.TaskStatus;
import com.sazzad.task.mappers.TaskListMapper;
import com.sazzad.task.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto)
                                .toList())
                        .orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks) {

        if(tasks == null){
            return null;
        }

        long closedTaskCount = tasks.stream().filter(task ->
                        TaskStatus.CLOSED == task.getStatus()
                ).count();

        return (double) closedTaskCount / tasks.size();
    }
}
