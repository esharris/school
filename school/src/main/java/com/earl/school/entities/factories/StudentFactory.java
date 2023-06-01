package com.earl.school.entities.factories;

import com.earl.school.entities.Student;

public interface StudentFactory {

	Student create(String firstName, String lastName, int gradeYear);

}