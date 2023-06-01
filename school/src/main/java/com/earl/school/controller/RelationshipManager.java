package com.earl.school.controller;

import com.earl.school.controller.dtos.StudentCoursePair;
import com.earl.school.entities.Course;
import com.earl.school.entities.Student;

public interface RelationshipManager {

	StudentCoursePair addCourse(Student student, Course course);

	StudentCoursePair addStudent(Course course, Student student);

}