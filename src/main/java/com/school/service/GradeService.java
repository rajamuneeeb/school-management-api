package com.school.service;


import com.school.dto.request.GradeRequest;
import com.school.dto.response.BaseResponse;
import com.school.entity.Enrollment;
import com.school.entity.Grade;
import com.school.repository.EnrollmentRepository;
import com.school.repository.GradeRepository;
import com.school.util.StatusCode;

import jakarta.transaction.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GradeService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Transactional
    public BaseResponse recordGrade(Long studentId, Long courseId, GradeRequest gradeRequest) {
        try {
            Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);

            if (enrollment == null) {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.ENROLLMENT_NOT_FOUND, null);
            }

            Grade grade = new Grade();
            grade.setEnrollment(enrollment);
            grade.setGrade(gradeRequest.getGrade());

            gradeRepository.save(grade);

            return new BaseResponse(StatusCode.CREATED_CODE, StatusCode.GRADE_RECORDED_SUCCESSFULLY, grade);
        } catch (Exception ex) {
            log.error("Error occurred while recording grade for student {} and course {}: ", studentId, courseId, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    public BaseResponse getGradesByStudentAndCourse(Long studentId, Long courseId) {
        try {
            List<Grade> grades = gradeRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId, courseId);

            if (grades.isEmpty()) {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.GRADE_NOT_FOUND, null);
            }

            return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.SUCCESS_MESSAGE, grades);
        } catch (Exception ex) {
            log.error("Error occurred while fetching grades for student {} and course {}: ", studentId, courseId, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }

}
