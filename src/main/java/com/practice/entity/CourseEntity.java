package com.practice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long numberStudents;
    private Long maxStudent;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<StudentEntity> students = new ArrayList<>();

    private Long courseCredits;

    public CourseEntity (){}

    public CourseEntity (String name, Long numberStudents, Long maxStudent,
                          List<StudentEntity> students, Long courseCredits ) {

        this.name = name;
        this.numberStudents = numberStudents;
        this.maxStudent = maxStudent;
        this.students = students;
        this.courseCredits = courseCredits;
    }
}
