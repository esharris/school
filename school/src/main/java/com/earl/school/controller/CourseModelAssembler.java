package com.earl.school.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.earl.school.entities.Course;

@Component
public class CourseModelAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {

	@Override
	public EntityModel<Course> toModel(Course entity) {
		return EntityModel.of(entity, linkTo(methodOn(SchoolController.class).retrieveAllCourses()).withRel("courses"),
				linkTo(methodOn(SchoolController.class).retrieveCourse(entity.getId())).withSelfRel());
	}

}
