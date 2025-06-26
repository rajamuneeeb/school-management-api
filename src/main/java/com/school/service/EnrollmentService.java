package com.school.service;


import com.school.dto.request.EnrollmentRequest;
import com.school.dto.response.BaseResponse;
import com.school.entity.Course;
import com.school.entity.Enrollment;
import com.school.entity.Student;
import com.school.repository.CourseRepository;
import com.school.repository.EnrollmentRepository;
import com.school.repository.StudentRepository;
import com.school.util.StatusCode;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class EnrollmentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional
    public BaseResponse enrollStudentInCourses(Long studentId, EnrollmentRequest enrollmentRequest) {
        try {
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(StatusCode.STUDENT_NOT_FOUND));

            List<Course> courses = courseRepository.findAllById(enrollmentRequest.getCourseIds());

            if (courses.isEmpty()) {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.NO_COURSES_FOUND, null);
            }

            for (Course course : courses) {
                boolean isAlreadyEnrolled = enrollmentRepository.existsByStudentIdAndCourseId(studentId, course.getId());

                if (isAlreadyEnrolled) {
                    return new BaseResponse(StatusCode.CONFLICT_CODE, "Course with ID " + course.getId() + " has already been taken by the student.", null);
                }

                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);

                enrollmentRepository.save(enrollment);
            }
            return new BaseResponse(StatusCode.CREATED_CODE, StatusCode.ENROLLMENT_SUCCESSFUL, null);
        } catch (Exception ex) {
            log.error("Error occurred while enrolling student {} in courses: ", studentId, ex);
            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }
}
