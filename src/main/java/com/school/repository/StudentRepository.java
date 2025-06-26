package com.school.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    Optional<Student> findByEmail(@Email(message = "Email should be valid") @NotEmpty(message = "Email cannot be empty") String email);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.grades g")
    List<Student> findAllStudentsWithDetails();


    @Query("SELECT s.id FROM Student s")
    Page<Long> findStudentIds(Pageable pageable);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.grades g WHERE s.id IN :studentIds")
    List<Student> findStudentsByIdsWithDetails(@Param("studentIds") List<Long> studentIds);
}
