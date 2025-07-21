package com.vay.synthetichumancorestarter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Command {
    @Size(max = 1000, message = "description size must be less 1000")
    @NotNull(message = "description must be set")
    private String description;

    @NotBlank(message = "priority must be set")
    private Priority priority;

    @Size(max = 100, message = "author size must be less 100")
    @NotBlank(message = "author must be set")
    private String author;

    @NotBlank(message = "time must be set")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?(?:Z|[+-]\\d{2}:?\\d{2})$",
        message = "time must be in ISO8601 format, e.g. 2024-07-10T15:30:00Z"
    )
    private String time;
}
