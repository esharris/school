package com.earl.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.earl.school.controller.dtos.StudentCoursePair;
import com.earl.school.controller.exceptions.LinkAlreadyThereException;
import com.earl.school.controller.exceptions.LinkNonexistentException;
import com.earl.school.entities.Course;
import com.earl.school.entities.Student;
import com.earl.school.jpa.CourseRepository;
import com.earl.school.jpa.StudentRepository;

@Component
public class RelationshipManagerImpl implements RelationshipManager {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;

	@Autowired
	public RelationshipManagerImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public StudentCoursePair addCourse(Student student, Course course) {
		if (!student.getCourseSet().contains(course)) {
			student.addCourse(course);
			return new StudentCoursePair(studentRepository.save(student), courseRepository.save(course));
		} else {
			throw new LinkAlreadyThereException(student.getStudentId(), course.getId());
		}
	}

	@Override
	public StudentCoursePair addStudent(Course course, Student student) {
		if (!course.getStudentSet().contains(student)) {
			course.addStudent(student);
			return new StudentCoursePair(studentRepository.save(student), courseRepository.save(course));
		} else {
			throw new LinkAlreadyThereException(student.getStudentId(), course.getId());
		}
	}

	@Override
	public void removeCourse(Student student, Course course) {
		if (student.getCourseSet().contains(course)) {
			student.removeCourse(course);
			studentRepository.save(student);
			courseRepository.save(course);
		} else {
			throw new LinkNonexistentException(student.getStudentId(), course.getId());
		}
	}

	@Override
	public void removeStudent(Course course, Student student) {
		if (course.getStudentSet().contains(student)) {
			course.removeStudent(student);
			courseRepository.save(course);
			studentRepository.save(student);
		} else {
			throw new LinkNonexistentException(student.getStudentId(), course.getId());
		}
	}
}
