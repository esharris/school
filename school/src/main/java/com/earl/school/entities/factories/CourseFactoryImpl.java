package com.earl.school.entities.factories;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.earl.school.entities.Course;

@Component
public class CourseFactoryImpl implements CourseFactory {

	@Override
	public Course create(String name) {
		return new Course(name, new HashSet<>());

	}
}
