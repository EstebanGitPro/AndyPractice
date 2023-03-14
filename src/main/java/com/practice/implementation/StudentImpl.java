package com.practice.implementation;

import com.practice.entity.StudentEntity;
import com.practice.exeption.ResourceNotFound;
import com.practice.repository.StudentRepository;
import com.practice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findAll ( ) {

        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentEntity findById ( Long id ) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("El estudiante con id: " + id + " no se encontro"));
    }

    @Override
    @Transactional
    public StudentEntity save ( StudentEntity student ) {

        return repository.save(student);
    }
    @Override
    @Transactional
    public void deleteById( Long id ) {

        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentEntity findByEmail( String email ) {

        return repository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail ( String email ) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findByAge ( Long age ) {

        return repository.findByAge(age);
    }




}
