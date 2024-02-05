package com.example.s3cur1ty.dto.request;

import com.example.s3cur1ty.domain.Task;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TaskRequestDto(

        @NotBlank
        @NotNull(message = "Name cannot be null")
        @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
        String name,

        @NotNull(message = "Description of Level cannot be Null")
        @NotBlank(message = "Description of Level cannot be blank")
        String description,

        @NotNull(message = "Date cannot be null")
        @FutureOrPresent(message = "Date must be in the Present or the future")
        LocalDateTime startDate,

        @Future(message = "End date should be in the future")
        @NotNull(message = "Date cannot be null")
        LocalDateTime endDate
) {

    public Task toTask() {
        return Task.builder()
                .name(this.name)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
