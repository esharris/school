package com.earl.school.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LinkAlreadyThereException extends RuntimeException {

	private static final long serialVersionUID = 6701760956991230190L;

	public LinkAlreadyThereException(String studentId, long courseId) {
		super("The student is already in the course. student id: " + studentId + ", course id: " + courseId);
	}

}
