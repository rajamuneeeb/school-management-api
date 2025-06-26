package com.school.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	private Student student;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@OneToMany(mappedBy = "enrollment" , fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	private Set<Grade> grades = new HashSet<>();
}
