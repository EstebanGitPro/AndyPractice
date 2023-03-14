package com.practice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "students_courses",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private List<CourseEntity> courses = new ArrayList<>();
    private Long registeredCredits;

    public StudentEntity () {}

    public StudentEntity (String name, String lastName,
                           String email, String age, List<CourseEntity> courses, Long registeredCredits ) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.courses = courses;
        this.registeredCredits = registeredCredits;
    }

    @Override
    public boolean equals ( Object obj ) {
        return super.equals(obj);
    }
}
