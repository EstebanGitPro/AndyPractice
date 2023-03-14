package com.practice.service;

import com.practice.entity.CourseEntity;
import com.practice.entity.StudentEntity;

import java.util.List;

public interface CourseService {


    public List<CourseEntity> findAll ( );

    public CourseEntity findById ( Long id );

    public CourseEntity save ( CourseEntity student );

    public void deleteById ( Long id );



}
