package com.school.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StudentRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
}
