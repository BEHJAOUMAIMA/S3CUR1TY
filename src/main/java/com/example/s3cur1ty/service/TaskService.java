package com.example.s3cur1ty.service;

import com.example.s3cur1ty.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task update(Task taskUpdated, Long id);
    void delete(Long id);

}
