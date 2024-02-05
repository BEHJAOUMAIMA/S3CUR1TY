package com.example.s3cur1ty.dto.response;

import com.example.s3cur1ty.domain.Task;

import java.time.LocalDateTime;

public record TaskResponseDto(
         Long id,
         String name,
         String description,
         LocalDateTime startDate,
         LocalDateTime endDate
) {

    public static TaskResponseDto fromTask(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getEndDate()
        );
    }
}
