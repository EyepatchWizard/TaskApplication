package com.sazzad.task.services.impl;

import com.sazzad.task.domain.entities.TaskList;
import com.sazzad.task.repositories.TaskListRepository;
import com.sazzad.task.services.TaskListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {

        if(null != taskList.getId()){
            throw new IllegalArgumentException("Task list already has an id!");
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must contain a title");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(
                new TaskList(
                        null,
                        taskList.getTitle(),
                        taskList.getDescription(),
                        null,
                        now,
                        now
                )
        );
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {

        if(null == taskList.getId()) {
            throw new IllegalArgumentException("Task list must contain ID!");
        }

        if(!Objects.equals(taskListId,taskList.getId())) {
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {

        taskListRepository.deleteById(taskListId);
    }
}
