package com.earl.school.controller.dtos;

import java.math.BigDecimal;

public record StudentUpdateInput(String firstName, String lastName, Integer gradeYear, BigDecimal tuitionBalance) {

}
