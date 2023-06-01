package com.earl.school.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.earl.school.controller.dtos.StudentAddInput;
import com.earl.school.controller.dtos.StudentUpdateInput;
import com.earl.school.controller.exceptions.CourseNotFoundException;
import com.earl.school.controller.exceptions.LinkNonexistentException;
import com.earl.school.controller.exceptions.StudentNotFoundException;
import com.earl.school.entities.Course;
import com.earl.school.entities.Student;
import com.earl.school.entities.factories.CourseFactory;
import com.earl.school.entities.factories.StudentFactory;
import com.earl.school.jpa.CourseRepository;
import com.earl.school.jpa.StudentRepository;

@RestController
public class SchoolController {

	private final StudentRepository studentRepository;

	private final CourseRepository courseRepository;

	private final RelationshipManager relationshipManager;

	private final RepresentationModelAssembler<Student, EntityModel<Student>> studentModelAssembler;

	private final RepresentationModelAssembler<Course, EntityModel<Course>> courseModelAssembler;

	private final StudentFactory studentFactory;

	private final CourseFactory courseFactory;

	@Autowired
	public SchoolController(StudentRepository studentRepository, //
			CourseRepository courseRepository, //
			RelationshipManager relationshipManager, //
			RepresentationModelAssembler<Student, EntityModel<Student>> studentModelAssembler,
			RepresentationModelAssembler<Course, EntityModel<Course>> courseModelAssembler,
			StudentFactory studentFactory, CourseFactory courseFactory) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.relationshipManager = relationshipManager;
		this.studentModelAssembler = studentModelAssembler;
		this.courseModelAssembler = courseModelAssembler;
		this.studentFactory = studentFactory;
		this.courseFactory = courseFactory;
	}

	@GetMapping("/students")
	public CollectionModel<EntityModel<Student>> retrieveAllStudents() {
		List<EntityModel<Student>> studentList = studentRepository.findAll().stream()
				.map(studentModelAssembler::toModel).toList();
		return CollectionModel.of(studentList,
				linkTo(methodOn(SchoolController.class).retrieveAllStudents()).withSelfRel());
	}

	@GetMapping("/courses")
	public CollectionModel<EntityModel<Course>> retrieveAllCourses() {
		List<EntityModel<Course>> courseList = courseRepository.findAll().stream().map(courseModelAssembler::toModel)
				.toList();
		return CollectionModel.of(courseList,
				linkTo(methodOn(SchoolController.class).retrieveAllCourses()).withSelfRel());
	}

	@GetMapping("students/{studentId}")
	public EntityModel<Student> retrieveStudent(@PathVariable String studentId) {
		Student student = getStudent(studentId);
		return studentModelAssembler.toModel(student);

	}

	@GetMapping("courses/{id}")
	public EntityModel<Course> retrieveCourse(@PathVariable long id) {
		Course course = getCourse(id);

		return courseModelAssembler.toModel(course);
	}

	@GetMapping("students/{studentId}/courses")
	public CollectionModel<EntityModel<Course>> retrieveCoursesForStudent(@PathVariable String studentId) {
		Student student = getStudent(studentId);
		List<EntityModel<Course>> courseList = student.getCourseSet().stream().map(courseModelAssembler::toModel)
				.toList();
		return CollectionModel.of(courseList,
				linkTo(methodOn(SchoolController.class).retrieveCoursesForStudent(studentId)).withSelfRel());
	}

	@GetMapping("courses/{id}/students")
	public CollectionModel<EntityModel<Student>> retrieveStudentsForCourse(@PathVariable long id) {
		Course course = getCourse(id);
		List<EntityModel<Student>> studenttList = course.getStudentSet().stream().map(studentModelAssembler::toModel)
				.toList();
		return CollectionModel.of(studenttList,
				linkTo(methodOn(SchoolController.class).retrieveStudentsForCourse(id)).withSelfRel());
	}

	@PostMapping("/students")
	public ResponseEntity<Student> newStudent(@RequestBody StudentAddInput studentAddInput) {
		Student student = studentFactory.create(studentAddInput.firstName(), studentAddInput.lastName(),
				studentAddInput.gradeYear());
		Student savedStudent = studentRepository.save(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentId}")
				.buildAndExpand(savedStudent.getStudentId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/courses")
	public ResponseEntity<Course> newCourse(@RequestBody String name) {
		Course course = courseFactory.create(name);
		Course savedCourse = courseRepository.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourse.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/students/{studentId}/courses/{id}")
	public ResponseEntity<Student> addCourse(@PathVariable String studentId, @PathVariable int id) {
		Student student = getStudent(studentId);
		Course course = getCourse(id);
		relationshipManager.addCourse(student, course);
		String locationString = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUriString();
		int i = locationString.lastIndexOf("/");
		URI location = URI.create(locationString.substring(0, i));
		return ResponseEntity.created(location).build();
	}

	@PostMapping("courses/{id}/students/{studentId}")
	public ResponseEntity<Course> addStudent(@PathVariable int id, @PathVariable String studentId) {
		Course course = getCourse(id);
		Student student = getStudent(studentId);
		relationshipManager.addStudent(course, student);
		String locationString = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUriString();
		int i = locationString.lastIndexOf("/");
		URI location = URI.create(locationString.substring(0, i));
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/students/{studentId}")
	public ResponseEntity<Student> replaceStudent(@PathVariable String studentId,
			@RequestBody StudentUpdateInput studentUpdateInput) {
		Student student = getStudent(studentId);
		student.setFirstName(studentUpdateInput.firstName());
		student.setLastName(studentUpdateInput.lastName());
		student.setGradeYear(studentUpdateInput.gradeYear());
		studentRepository.save(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<Course> replaceCourse(@PathVariable int id, @RequestBody String newCourseName) {
		Course course = getCourse(id);
		course.setName(newCourseName);
		courseRepository.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("students/{studentId}")
	public ResponseEntity<Student> deleteStudent(@PathVariable String studentId) {
		Student student = getStudent(studentId);
		/**
		 * Remove the student from every course he/she enrolled in.
		 */
		for (Course course : student.getCourseSet()) {
			course.getStudentSet().remove(student);
			courseRepository.save(course);
		}
		studentRepository.delete(student);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("courses/{id}")
	public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
		Course course = getCourse(id);
		/**
		 * Remove the course from every student that has the course scheduled.
		 */
		for (Student student : course.getStudentSet()) {
			student.getCourseSet().remove(course);
			studentRepository.save(student);
		}
		courseRepository.delete(course);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("students/{studentId}/courses/{id}")
	public ResponseEntity<Course> removeStudentFromCourse(@PathVariable String studentId, @PathVariable int id) {
		Student student = getStudent(studentId);
		Course course = getCourse(id);

		if (course.getStudentSet().contains(student)) {
			course.removeStudent(student);
			courseRepository.save(course);
			studentRepository.save(student);
			return ResponseEntity.noContent().build();
		} else {
			throw new LinkNonexistentException(studentId, id);
		}
	}

	@DeleteMapping("courses/{id}/students/{studentId}")
	public ResponseEntity<Student> removeCourseFromStudent(@PathVariable int id, @PathVariable String studentId) {
		Course course = getCourse(id);
		Student student = getStudent(studentId);

		if (student.getCourseSet().contains(course)) {
			student.removeCourse(course);
			studentRepository.save(student);
			courseRepository.save(course);
			return ResponseEntity.noContent().build();
		} else {
			throw new LinkNonexistentException(studentId, id);
		}
	}

	private Student getStudent(String studentId) {
		Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);

		Student student = studentOptional.orElseThrow(() -> new StudentNotFoundException(studentId));
		return student;
	}

	private Course getCourse(long id) {
		Optional<Course> courseOptional = courseRepository.findById(id);
		Course course = courseOptional.orElseThrow(() -> new CourseNotFoundException(id));
		return course;
	}
}
