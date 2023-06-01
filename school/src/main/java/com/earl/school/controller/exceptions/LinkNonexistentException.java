package com.earl.school.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LinkNonexistentException extends RuntimeException {

	private static final long serialVersionUID = 6316087866617034986L;

	public LinkNonexistentException(String studentId, long courseId) {
		super("The student is not in the course. student id: " + studentId + ", course id: " + courseId);
	}

}
