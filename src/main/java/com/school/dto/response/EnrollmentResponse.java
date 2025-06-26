package com.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private CourseResponse course;
    private Set<GradeResponse> grades;
}
