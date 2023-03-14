package com.practice.repository;

import com.practice.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    public StudentEntity findByEmail(String email);
    public boolean existsByEmail ( String email);
    public List<StudentEntity> findByAge( Long age);

}
