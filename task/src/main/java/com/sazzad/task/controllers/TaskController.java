package com.sazzad.task.controllers;

import com.sazzad.task.domain.dto.TaskDto;
import com.sazzad.task.domain.entities.Task;
import com.sazzad.task.mappers.TaskMapper;
import com.sazzad.task.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists/{task_list_id}")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @RequestMapping(path = "/tasks")
    List<TaskDto> listTask(@PathVariable("task_list_id") UUID taskListId) {

        return taskService
                .listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping(path = "/tasks")
    TaskDto createTask(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskDto taskDto
    ) {

        Task savedTask = taskService.createTask(
                taskListId,
                taskMapper.fromDto(taskDto));

        return taskMapper.toDto(savedTask);
    }

    @GetMapping(path = "/tasks/{task_id}")
    Optional<Task> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ) {

        return taskService.geTask(taskListId, taskId);
    }

    @PutMapping(path = "/tasks/{task_id}")
    TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ) {

        Task updatedTask = taskService.updateTask(
                taskListId,
                taskId,
                taskMapper.fromDto(taskDto)
        );

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/tasks/{task_id}")
    public void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ) {
        taskService.deleteTask(taskListId, taskId);
    }
}
