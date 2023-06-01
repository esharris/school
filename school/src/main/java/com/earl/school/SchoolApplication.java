package com.earl.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.earl.school.controller.RelationshipManager;
import com.earl.school.entities.Course;
import com.earl.school.entities.Student;
import com.earl.school.entities.factories.CourseFactory;
import com.earl.school.entities.factories.StudentFactory;
import com.earl.school.jpa.CourseRepository;
import com.earl.school.jpa.StudentRepository;

@SpringBootApplication
public class SchoolApplication {

	private final StudentFactory studentFactory;

	private final CourseFactory courseFactory;

	@Autowired
	public SchoolApplication(StudentFactory studentFactory, CourseFactory courseFactory) {
		this.studentFactory = studentFactory;
		this.courseFactory = courseFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(StudentRepository studentRepository, CourseRepository courseRepository) {
		return args -> {
			Student student1 = studentFactory.create("Earl", "Harris", 3);
			Student student2 = studentFactory.create("Daniella", "Hines", 2);
			Student student3 = studentFactory.create("Joe", "Welch", 2);

			Student student1a = studentRepository.save(student1);
			studentRepository.save(student2);
			Student student3a = studentRepository.save(student3);

			Course course1 = courseFactory.create("Chem 10");
			Course course2 = courseFactory.create("Comp Sci 10");
			Course course3 = courseFactory.create("Nigs in Flicks");

			courseRepository.save(course1);
			Course course2a = courseRepository.save(course2);
			courseRepository.save(course3);

//			student1a.addCourse(course2a);
//			studentRepository.save(student1a);
//			Course course2b = courseRepository.save(course2a);
//
//			course2b.addStudent(student3a);
//			courseRepository.save(course2b);
//			studentRepository.save(student3a);

			Course course2b = RelationshipManager.addCourse(studentRepository, courseRepository, student1a, course2a)
					.course();
			RelationshipManager.addStudent(courseRepository, studentRepository, course2b, student3a);
		};

	}
}
