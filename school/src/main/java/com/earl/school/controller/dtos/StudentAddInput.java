package com.earl.school.controller.dtos;

import java.time.LocalDate;

public record StudentAddInput(String firstName, String lastName, LocalDate birthDate, int gradeYear) {

}
