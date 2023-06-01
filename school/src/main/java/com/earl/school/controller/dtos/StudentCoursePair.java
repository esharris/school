package com.earl.school.controller.dtos;

import com.earl.school.entities.Course;
import com.earl.school.entities.Student;

public record StudentCoursePair(Student student, Course course) {

}
