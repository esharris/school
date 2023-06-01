package com.earl.school.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.earl.school.entities.Student;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {

	@Override
	public EntityModel<Student> toModel(Student entity) {
		return EntityModel.of(entity, //
				linkTo(methodOn(SchoolController.class).retrieveAllStudents()).withRel("students"),
				linkTo(methodOn(SchoolController.class).retrieveStudent(entity.getStudentId())).withSelfRel());
	}

}
