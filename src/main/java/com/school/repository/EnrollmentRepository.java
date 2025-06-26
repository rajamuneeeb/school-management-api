package com.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

	Enrollment findByStudentIdAndCourseId(Long studentId, Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long id);
}
