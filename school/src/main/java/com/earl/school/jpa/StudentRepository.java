package com.earl.school.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.earl.school.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByStudentId(String studentId);
}
