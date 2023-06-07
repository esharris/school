package com.earl.school.entities;

import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;
	private String name;

	@ManyToMany(targetEntity = Student.class)
	@JsonIgnore
	private Set<Student> studentSet;

	public Course() {

	}

	public Course(String name, Set<Student> studentSet) {
		super();
		this.name = name;
		this.studentSet = studentSet;

		for (Student student : studentSet) {
			student.getCourseSet().add(this);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Student> getStudentSet() {
		return studentSet;
	}

	public void setStudentSet(Set<Student> studentSet) {
		this.studentSet = studentSet;
	}

	public void addStudent(Student student) {
		this.getStudentSet().add(student);
		student.getCourseSet().add(this);
	}

	public void removeStudent(Student student) {
		this.getStudentSet().remove(student);
		student.getCourseSet().remove(this);
	}

	public void removeAllStudents(Function<Student, Student> studentFunction) {
		/**
		 * Remove the course from every student that has the course scheduled.
		 */
		for (Student student : this.getStudentSet()) {
			student.getCourseSet().remove(this);
			studentFunction.apply(student);
		}
		this.getStudentSet().clear();
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}

}
