package com.earl.school.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentUpdateInput(String firstName, String lastName, LocalDate birthDate, Integer gradeYear,
		BigDecimal tuitionBalance) {

}
