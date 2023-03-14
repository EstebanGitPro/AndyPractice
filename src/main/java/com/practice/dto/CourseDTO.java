package com.practice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CourseDTO {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private Long numberStudents;

    @NotNull
    @NotBlank
    private Long maxStudent;
}
