package com.practice.implementation;

import com.practice.entity.CourseEntity;
import com.practice.exeption.ResourceNotFound;
import com.practice.repository.CourseRepository;
import com.practice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public List<CourseEntity> findAll ( ) {

        return repository.findAll();
    }

    @Override
    public CourseEntity findById ( Long id ) {

        return repository.findById(id).orElseThrow(()-> new ResourceNotFound("El curso con id " + id + " no se encontro" ));
    }

    @Override
    public CourseEntity save ( CourseEntity course ) {

        return repository.save(course);
    }

    @Override
    public void deleteById ( Long id ) {

        repository.deleteById(id);
    }




}
