package com.earl.school.entities.factories;

import java.time.LocalDate;

import com.earl.school.entities.Student;

public interface StudentFactory {

	Student create(String firstName, String lastName, LocalDate birthDate, int gradeYear);

}