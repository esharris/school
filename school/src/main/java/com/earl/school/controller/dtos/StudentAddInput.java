package com.earl.school.controller.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;

public record StudentAddInput(String firstName, String lastName,
		@Past(message = "Birth Date should be in the past") LocalDate birthDate, int gradeYear) {

}
