package com.example.s3cur1ty.service.impl;

import com.example.s3cur1ty.domain.Task;
import com.example.s3cur1ty.handler.exception.OperationException;
import com.example.s3cur1ty.repository.TaskRepository;
import com.example.s3cur1ty.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {

        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            throw new OperationException("No Tasks found");
        }

        return tasks;

    }

    @Override
    public Optional<Task> findById(Long id) {

        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        return taskRepository.findById(id);

    }

    @Override
    public Task update(Task taskUpdated, Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task existingTask = task.get();
            existingTask.setName(taskUpdated.getName());
            existingTask.setDescription(taskUpdated.getDescription());
            existingTask.setStartDate(taskUpdated.getStartDate());
            existingTask.setEndDate(taskUpdated.getEndDate());
            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {

        if (id <= 0) {
            throw new OperationException("ID must be greater than 0");
        }

        Optional<Task> level = taskRepository.findById(id);
        if (level.isEmpty()) {
            throw new OperationException("Task not found with ID: " + id);
        }

        taskRepository.deleteById(id);
    }
}
