package com.school.util;

public class StatusCode {

    public static final String SUCCESS_CODE = "200";
    public static final String CREATED_CODE = "201";
    public static final String NOT_FOUND_CODE = "404";
    public static final String BAD_REQUEST_CODE = "400";
    
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String STUDENT_CREATED_SUCCESSFULLY = "Student Created Successfully";
    public static final String STUDENT_UPDATED_SUCCESSFULLY = "Student Updated Successfully";
    public static final String STUDENT_DELETED_SUCCESSFULLY = "Student Deleted Successfully";
    public static final String STUDENT_NOT_FOUND = "Student Not Found";
    public static final String STUDENT_ALREADY_EXISTS = "Student with this email already exists!";


    public static final String COURSE_CREATED_SUCCESSFULLY = "Course Created Successfully";
    public static final String COURSE_UPDATED_SUCCESSFULLY = "Course Updated Successfully";
    public static final String COURSE_DELETED_SUCCESSFULLY = "Course Deleted Successfully";
    public static final String COURSE_NOT_FOUND = "Course Not Found";
    public static final String COURSE_ALREADY_EXISTS = "Course with name already exist!";
    
    
    public static final String ENROLLMENT_SUCCESSFUL = "Student enrolled in courses successfully";
    public static final String GRADE_RECORDED_SUCCESSFULLY = "Grade recorded successfully";
    public static final String GRADE_NOT_FOUND = "Grade not found for this student and course";
    public static final String NO_COURSES_FOUND = "No courses found for enrollment";
    public static final String ENROLLMENT_NOT_FOUND = "Enrollment not found";



    public static final String INTERNAL_SERVER_ERROR_CODE = "500";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";


    public static final String CONFLICT_CODE = "409";
}
