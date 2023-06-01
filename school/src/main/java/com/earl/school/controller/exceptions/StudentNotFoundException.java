package com.earl.school.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2602163346745017290L;

	public StudentNotFoundException(String studentId) {
		super("studentId: " + studentId);
	}
}
