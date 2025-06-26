package com.school.controller;

import com.school.dto.request.EnrollmentRequest;
import com.school.dto.response.BaseResponse;
import com.school.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/students/{studentId}")
    public BaseResponse enrollStudentInCourses(@PathVariable Long studentId, @RequestBody EnrollmentRequest enrollmentRequest) {
        return enrollmentService.enrollStudentInCourses(studentId, enrollmentRequest);
    }
}

