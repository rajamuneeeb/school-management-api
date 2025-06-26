package com.school.controller;


import com.school.dto.request.CourseRequest;
import com.school.dto.response.BaseResponse;
import com.school.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public BaseResponse createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }

    @GetMapping
    public BaseResponse getAllCourses(Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @GetMapping("/{id}")
    public BaseResponse getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    
    @PutMapping("/{id}")
    public BaseResponse updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest courseRequest) {
        return courseService.updateCourse(id, courseRequest);
    }

    
    @DeleteMapping("/{id}")
    public BaseResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
