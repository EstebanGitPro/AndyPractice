package com.practice.dto;

import com.practice.entity.CourseEntity;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class StudentDTO {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull(message = "no dejar espacio en blanco")
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 2)
    private String age;

    @NotNull
    private List<CourseEntity> courses;

    @NotNull
    private Long registeredCredits;





}
