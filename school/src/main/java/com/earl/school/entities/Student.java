package com.earl.school.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Past;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	private String studentId;
	private String firstName;
	private String lastName;
	@Past(message = "Birth Date should be in the past")
	private LocalDate birthDate;
	private Integer gradeYear;

	@ManyToMany(targetEntity = Course.class)
	@JsonIgnore
	private Set<Course> courseSet;

	private BigDecimal tuitionBalance;

	public Student() {

	}

	public Student(String studentId, String firstName, String lastName, LocalDate birthDate, Integer gradeYear,
			Set<Course> courseSet, BigDecimal tuitionBalance) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gradeYear = gradeYear;
		this.courseSet = courseSet;
		this.tuitionBalance = tuitionBalance;

		for (Course course : courseSet) {
			course.getStudentSet().add(this);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentID) {
		this.studentId = studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {
		this.gradeYear = gradeYear;
	}

	public Set<Course> getCourseSet() {
		return courseSet;
	}

	public void setCourseSet(Set<Course> courseSet) {
		this.courseSet = courseSet;
	}

	public void addCourse(Course course) {
		this.courseSet.add(course);
		course.getStudentSet().add(this);
	}

	public void removeCourse(Course course) {
		this.getCourseSet().remove(course);
		course.getStudentSet().remove(this);
	}

	public void removeAllCourses(Function<Course, Course> courseFunction) {
		/**
		 * Remove the student from every course he/she enrolled in.
		 */
		for (Course course : this.getCourseSet()) {
			course.getStudentSet().remove(this);
			courseFunction.apply(course);
		}
		this.getCourseSet().clear();
	}

	public BigDecimal getTuitionBalance() {
		return tuitionBalance;
	}

	public void setTuitionBalance(BigDecimal tuitionBalance) {
		this.tuitionBalance = tuitionBalance;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", studentID=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthDate=" + birthDate + ", gradeYear=" + gradeYear + ", tuitionBalance=" + tuitionBalance + "]";
	}

}
