package com.practice.controller;

import com.practice.entity.CourseEntity;
import com.practice.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public CourseService coursesServices;

    @GetMapping
    public List<CourseEntity> coursesList(){
        return coursesServices.findAll();
    }

    @GetMapping("/{id}")
    public CourseEntity  findById(Long id){
        return coursesServices.findById(id);
    }

    @PostMapping("/createCourse")
    public CourseEntity create( @RequestBody CourseEntity course){

        return coursesServices.save(course);

    }

    @PutMapping("/{id}")
    public CourseEntity edit(@RequestBody CourseEntity course, @PathVariable Long id){

        CourseEntity courseDatabase = null;
        courseDatabase = coursesServices.findById(id);

        CourseEntity updateCourse;
        updateCourse = new CourseEntity(course.getName(),course.getNumberStudents(), course.getMaxStudent(), course.getStudents() ,course.getCourseCredits());

        courseDatabase.setName(updateCourse.getName());
        courseDatabase.setNumberStudents(updateCourse.getNumberStudents());
        courseDatabase.setMaxStudent(updateCourse.getMaxStudent());
        courseDatabase.setStudents(updateCourse.getStudents());
        courseDatabase.setCourseCredits(updateCourse.getCourseCredits());

        return coursesServices.save(course);
    }

    @DeleteMapping("/{id}")
    public void  delete(@PathVariable Long id){

        coursesServices.deleteById(id);
    }


}
