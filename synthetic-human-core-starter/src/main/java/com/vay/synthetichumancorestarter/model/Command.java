package com.vay.synthetichumancorestarter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Command {
    @Size(max = 1000, message = "description size must be less 1000")
    @NotNull(message = "description must be set")
    private String description;

    @NotNull(message = "priority must be set")
    private Priority priority;

    @Size(max = 100, message = "author size must be less 100")
    @NotBlank(message = "author must be set")
    private String author;

    private String time = LocalDate.now().toString();
}
