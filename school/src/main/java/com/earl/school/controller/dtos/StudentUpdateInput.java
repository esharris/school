package com.earl.school.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Past;

public record StudentUpdateInput(String firstName, String lastName,
		@Past(message = "Birth Date should be in the past") LocalDate birthDate, Integer gradeYear,
		BigDecimal tuitionBalance) {

}
