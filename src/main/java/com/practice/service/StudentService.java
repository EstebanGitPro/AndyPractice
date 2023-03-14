package com.practice.service;

import com.practice.controller.StudentController;
import com.practice.entity.CourseEntity;
import com.practice.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {

    public List<StudentEntity> findAll ( );

    public StudentEntity findById ( Long id );

    public StudentEntity save ( StudentEntity student );

    public void deleteById ( Long id );

    public StudentEntity findByEmail ( String email );

    public boolean existsByEmail ( String email );

    public List<StudentEntity> findByAge ( Long age );


}
