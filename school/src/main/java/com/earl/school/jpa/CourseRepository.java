package com.earl.school.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.earl.school.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
