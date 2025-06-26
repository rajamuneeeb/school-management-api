package com.school.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class EnrollmentRequest {
	private List<Long> courseIds;

}