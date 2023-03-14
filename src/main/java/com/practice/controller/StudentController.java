package com.practice.controller;


import com.practice.dto.StudentDTO;
import com.practice.entity.CourseEntity;
import com.practice.entity.StudentEntity;
import com.practice.exeption.ResourceBadRequest;
import com.practice.service.CourseService;
import com.practice.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {


    private Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentService studentServices;

    @Autowired
    public CourseService courseService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> studentList(){

        List<StudentEntity> students = null;

        Map<String, Object> response = new HashMap<>();

        try {

            students = studentServices.findAll();
            //return studentServices.findAll();

        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("student", students);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);



    }

    @GetMapping("/{id}")
    public StudentEntity findById( @PathVariable Long id){
        return studentServices.findById(id);
    }


    @PostMapping("/createStudent")
    public StudentEntity create(@Valid @RequestBody StudentDTO studentDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new ResourceBadRequest("", bindingResult);
        }

        StudentEntity student = modelMapper.map(studentDto, StudentEntity.class);

        return studentServices.save(student);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody StudentDTO student,BindingResult result,@PathVariable Long id ) {

        StudentEntity studentDataBase = null;
        studentDataBase = studentServices.findById(id);
        StudentEntity updateStudent;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "El campo '" + err.getField() + "'" + err.getDefaultMessage();
                    }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {

            studentDataBase = studentServices.findById(id);
            if (studentDataBase == null) {
                response.put("mensaje", "El empleado con id: " + id + " no existe");
                return new ResponseEntity<Map< String,Object >>(response, HttpStatus.NOT_FOUND);
            }
            if (studentDataBase.getEmail().equals(student.getEmail())) {
                response.put("mensaje", "El email no coincide con el existente");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }


            updateStudent = new StudentEntity(student.getName(), student.getLastName(),
                    student.getEmail(), student.getAge(), student.getCourses(), student.getRegisteredCredits());

            studentDataBase.setName(updateStudent.getName());
            studentDataBase.setLastName(updateStudent.getLastName());
            studentDataBase.setEmail(updateStudent.getEmail());
            studentDataBase.setAge(updateStudent.getAge());
            studentDataBase.setCourses(updateStudent.getCourses());
            studentDataBase.setRegisteredCredits(updateStudent.getRegisteredCredits());

            studentDataBase = studentServices.save(studentDataBase);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("student", studentDataBase);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        studentServices.deleteById(id);
    }

    @PostMapping("/{studentId}/course/{courseId}")
    public StudentEntity registerCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        StudentEntity student = studentServices.findById(studentId);
        CourseEntity course = courseService.findById(courseId);

        Map<String, Object> response = new HashMap<>();

        List<CourseEntity> newCourses = student.getCourses();
        // Validar que el curso no este matriculado y
        String toFind = course
        boolean exists = newCourses.contains(toFind);
        if(exists){
            response.put("mensaje", "El curso" +course.getName() +" ya se encuentra matriculado");

        }
        // no tenga mas de los creditos permitidos

        newCourses.add(course);
        student.setCourses(newCourses);
        // ver si al llamar el curso me aparece este nuevo alumno
        return studentServices.save(student);
    }
}
