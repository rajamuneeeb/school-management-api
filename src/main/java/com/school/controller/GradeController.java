package com.school.controller;

import com.school.dto.request.GradeRequest;
import com.school.dto.response.BaseResponse;
import com.school.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    
    
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public BaseResponse recordGrade(@PathVariable Long studentId, @PathVariable Long courseId, @RequestBody GradeRequest gradeRequest) {
        return gradeService.recordGrade(studentId, courseId, gradeRequest);
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public BaseResponse getGrades(@PathVariable Long studentId, @PathVariable Long courseId) {
        return gradeService.getGradesByStudentAndCourse(studentId, courseId);
    }
}
