package com.school.service;

import com.school.dto.request.StudentRequest;
import com.school.dto.response.*;
import com.school.entity.Enrollment;
import com.school.entity.Grade;
import com.school.entity.Student;
import com.school.repository.StudentRepository;
import com.school.util.StatusCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public BaseResponse createStudent(StudentRequest studentRequest) {
        try {
            Optional<Student> existingStudent = studentRepository.findByEmail(studentRequest.getEmail());

            if (existingStudent.isPresent()) {
                return new BaseResponse(StatusCode.BAD_REQUEST_CODE, StatusCode.STUDENT_ALREADY_EXISTS, null);
            }

            Student student = new Student();
            student.setName(studentRequest.getName());
            student.setEmail(studentRequest.getEmail());

            studentRepository.save(student);

            return new BaseResponse(StatusCode.CREATED_CODE, StatusCode.STUDENT_CREATED_SUCCESSFULLY, null);
        } catch (Exception ex) {
            log.error("Error occurred while creating student: ", ex);
            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    @Transactional
    public BaseResponse getAllStudents(Pageable pageable) {
        try {

            Page<Long> studentIdsPage = studentRepository.findStudentIds(pageable);
            List<Long> studentIds = studentIdsPage.getContent();

            List<Student> students;
            if (studentIds.isEmpty()) {
                students = List.of();
            } else {
                students = studentRepository.findStudentsByIdsWithDetails(studentIds);
            }

            students.forEach(student -> log.debug("Student ID: {}, Enrollments size: {}", student.getId(), student.getEnrollments().size()));

            List<StudentResponse> studentDTOs = students.stream().map(student -> {

                Set<EnrollmentResponse> enrollmentResponses = new HashSet<>();

                if (student.getEnrollments() != null && !student.getEnrollments().isEmpty()) {
                    for (Enrollment enrollment : student.getEnrollments()) {

                        Set<GradeResponse> gradeResponses = new HashSet<>();

                        for (Grade grade : enrollment.getGrades()) {
                            gradeResponses.add(new GradeResponse(grade.getId(), grade.getGrade()));
                        }
                        enrollmentResponses.add(new EnrollmentResponse(enrollment.getId(), new CourseResponse(enrollment.getCourse().getId(), enrollment.getCourse().getName()), gradeResponses));
                    }
                }
                return new StudentResponse(student.getId(), student.getName(), student.getEmail(), enrollmentResponses.isEmpty() ? null : enrollmentResponses);
            }).collect(Collectors.toList());

            return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.SUCCESS_MESSAGE, studentDTOs);
        } catch (Exception ex) {
            log.error("Error occurred while fetching all students: ", ex);
            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    @Transactional
    public BaseResponse getStudent(Long id) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);

            if (studentOptional.isPresent()) {
                Student studentData = studentOptional.get();
                StudentResponse studentResponse;

                if (studentData.getEnrollments() == null || studentData.getEnrollments().isEmpty()) {
                    studentResponse = new StudentResponse(studentData.getId(), studentData.getName(), studentData.getEmail(), null);
                } else {
                    Set<EnrollmentResponse> enrollmentResponses = studentData.getEnrollments().stream().map(enrollment -> {
                        Set<GradeResponse> gradeResponses = enrollment.getGrades().stream().map(grade -> new GradeResponse(grade.getId(), grade.getGrade())).collect(Collectors.toSet());

                        return new EnrollmentResponse(enrollment.getId(), new CourseResponse(enrollment.getCourse().getId(), enrollment.getCourse().getName()), gradeResponses);
                    }).collect(Collectors.toSet());

                    studentResponse = new StudentResponse(studentData.getId(), studentData.getName(), studentData.getEmail(), enrollmentResponses);
                }
                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.SUCCESS_MESSAGE, studentResponse);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.STUDENT_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while fetching student with ID {}: ", id, ex);
            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    @Transactional
    public BaseResponse updateStudent(Long id, StudentRequest studentRequest) {
        try {
            Optional<Student> existingStudent = studentRepository.findById(id);

            if (existingStudent.isPresent()) {
                Student student = existingStudent.get();
                student.setName(studentRequest.getName());
                student.setEmail(studentRequest.getEmail());

                Student updatedStudent = studentRepository.save(student);

                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.STUDENT_UPDATED_SUCCESSFULLY, updatedStudent);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.STUDENT_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while updating student with ID {}: ", id, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    @Transactional
    public BaseResponse deleteStudent(Long id) {
        try {
            Optional<Student> student = studentRepository.findById(id);

            if (student.isPresent()) {
                studentRepository.deleteById(id);

                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.STUDENT_DELETED_SUCCESSFULLY, null);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.STUDENT_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while deleting student with ID {}: ", id, ex);
            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }

}
