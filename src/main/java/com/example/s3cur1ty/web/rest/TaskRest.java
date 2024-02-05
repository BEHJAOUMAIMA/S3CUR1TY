package com.example.s3cur1ty.web.rest;

import com.example.s3cur1ty.domain.Task;
import com.example.s3cur1ty.dto.request.TaskRequestDto;
import com.example.s3cur1ty.dto.response.TaskResponseDto;
import com.example.s3cur1ty.handler.response.ResponseMessage;
import com.example.s3cur1ty.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskRest {

    private final TaskService taskService;

    @GetMapping("/view")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        List<TaskResponseDto> taskDTOs = tasks.stream()
                .map(TaskResponseDto::fromTask)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseMessage> addTask(@Valid @RequestBody TaskRequestDto taskDTO) {
        Task savedTask = taskService.save(taskDTO.toTask());

        if (savedTask == null) {
            return ResponseMessage.badRequest("Task not created");
        } else {
            return ResponseMessage.created("Task created successfully", savedTask);
        }
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);

        if (task.isEmpty()) {
            return ResponseMessage.notFound("Task not found with ID: " + id);
        }

        TaskResponseDto taskDTO = TaskResponseDto.fromTask(task.get());
        return ResponseEntity.ok(taskDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto taskDTO) {
        Optional<Task> existingTask = taskService.findById(id);

        if (existingTask.isEmpty()) {
            return ResponseMessage.notFound("Task not found with ID: " + id);
        }

        Task updatedTask = taskDTO.toTask();
        updatedTask.setId(id);

        Task result = taskService.update(updatedTask, id);

        if (result == null) {
            return ResponseMessage.badRequest("Failed to update Task with ID: " + id);
        }

        TaskResponseDto updatedTaskDTO = TaskResponseDto.fromTask(result);
        return ResponseMessage.ok("Task updated successfully with ID: " + id, updatedTaskDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Optional<Task> existingTask = taskService.findById(id);

        if (existingTask.isEmpty()) {
            return ResponseMessage.notFound("Task not found with ID: " + id);
        }

        taskService.delete(id);

        return ResponseMessage.ok("Task deleted successfully with ID: " + id, null);
    }

}
