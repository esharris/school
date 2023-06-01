package com.earl.school.controller;

import com.earl.school.controller.dtos.StudentCoursePair;
import com.earl.school.controller.exceptions.LinkAlreadyThereException;
import com.earl.school.entities.Course;
import com.earl.school.entities.Student;
import com.earl.school.jpa.CourseRepository;
import com.earl.school.jpa.StudentRepository;

public class RelationshipManager {

	public static StudentCoursePair addCourse(StudentRepository studentRepository, CourseRepository courseRepository,
			Student student, Course course) {
		if (!student.getCourseSet().contains(course)) {
			student.addCourse(course);
			return new StudentCoursePair(studentRepository.save(student), courseRepository.save(course));
		} else {
			throw new LinkAlreadyThereException(student.getStudentId(), course.getId());
		}
	}

	public static StudentCoursePair addStudent(CourseRepository courseRepository, StudentRepository studentRepository,
			Course course, Student student) {
		if (!course.getStudentSet().contains(student)) {
			course.addStudent(student);
			return new StudentCoursePair(studentRepository.save(student), courseRepository.save(course));
		} else {
			throw new LinkAlreadyThereException(student.getStudentId(), course.getId());
		}

	}
}
