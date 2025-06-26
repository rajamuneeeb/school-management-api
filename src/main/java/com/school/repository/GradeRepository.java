package com.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long>{

	List<Grade> findByEnrollmentStudentIdAndEnrollmentCourseId(Long studentId, Long courseId);

}
