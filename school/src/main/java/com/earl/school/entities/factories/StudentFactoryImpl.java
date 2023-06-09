package com.earl.school.entities.factories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.earl.school.entities.Student;

@Component
public class StudentFactoryImpl implements StudentFactory {

	private long id = 10000;

	@Override
	public Student create(String firstName, String lastName, LocalDate birthDate, int gradeYear) {
		String studentId = String.valueOf(id);
		id++;
		return new Student(studentId, firstName, lastName, birthDate, gradeYear, new HashSet<>(), BigDecimal.ZERO);

	}
}
