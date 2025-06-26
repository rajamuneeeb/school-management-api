package com.school.service;

import com.school.dto.request.CourseRequest;
import com.school.dto.response.BaseResponse;
import com.school.dto.response.CourseResponse;
import com.school.entity.Course;
import com.school.repository.CourseRepository;
import com.school.util.StatusCode;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public BaseResponse createCourse(CourseRequest courseRequest) {
        try {
            Optional<Course> existingCourse = courseRepository.findByName(courseRequest.getName());

            if (existingCourse.isPresent()) {
                return new BaseResponse(StatusCode.BAD_REQUEST_CODE, StatusCode.COURSE_ALREADY_EXISTS, null);
            }
            Course course = new Course();
            course.setName(courseRequest.getName());

            Course savedCourse = courseRepository.save(course);
            CourseResponse courseResponse = new CourseResponse(savedCourse.getId(), savedCourse.getName());

            return new BaseResponse(StatusCode.CREATED_CODE, StatusCode.COURSE_CREATED_SUCCESSFULLY, courseResponse);
        } catch (Exception ex) {
            log.error("Error occurred while creating course: ", ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }


    public BaseResponse getAllCourses(Pageable pageable) {
        try {
            Page<Course> courses = courseRepository.findAll(pageable);

            List<CourseResponse> courseResponses = courses.stream().map(course -> new CourseResponse(course.getId(), course.getName())).collect(Collectors.toList());

            return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.SUCCESS_MESSAGE, courseResponses);
        } catch (Exception ex) {
            log.error("Error occurred while fetching all courses: ", ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }

    public BaseResponse getCourse(Long id) {
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                CourseResponse courseResponse = new CourseResponse(course.get().getId(), course.get().getName());
                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.SUCCESS_MESSAGE, courseResponse);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.COURSE_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while fetching course with ID {}: ", id, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }

    public BaseResponse updateCourse(Long id, CourseRequest courseRequest) {
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                Course updatedCourse = course.get();
                updatedCourse.setName(courseRequest.getName());

                courseRepository.save(updatedCourse);

                CourseResponse courseResponse = new CourseResponse(updatedCourse.getId(), updatedCourse.getName());

                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.COURSE_UPDATED_SUCCESSFULLY, courseResponse);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.COURSE_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while updating course with ID {}: ", id, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }

    @Transactional
    public BaseResponse deleteCourse(Long id) {
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                courseRepository.deleteById(id);
                return new BaseResponse(StatusCode.SUCCESS_CODE, StatusCode.COURSE_DELETED_SUCCESSFULLY, null);
            } else {
                return new BaseResponse(StatusCode.NOT_FOUND_CODE, StatusCode.COURSE_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            log.error("Error occurred while deleting course with ID {}: ", id, ex);

            return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR_CODE, StatusCode.INTERNAL_SERVER_ERROR_MESSAGE, null);
        }
    }
}

