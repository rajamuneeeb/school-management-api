package com.school.controller;

import com.school.dto.request.StudentRequest;
import com.school.dto.response.BaseResponse;
import com.school.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

   
    @PostMapping
    public BaseResponse createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @GetMapping
    public BaseResponse getAllStudents(Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    @GetMapping("/{id}")
    public BaseResponse getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/{id}")
    public BaseResponse updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }
    
    @DeleteMapping("/{id}")
    public BaseResponse deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
