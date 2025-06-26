package com.school.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CourseRequest {
    @NotEmpty(message = "Course name cannot be empty")
    private String name;
}
