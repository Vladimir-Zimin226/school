package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public Student editeStudent(@RequestBody Student student, @PathVariable Long id) {
        return studentService.editStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public ResponseEntity findStudent(@RequestParam(required = false) String name, @RequestParam(required = false) Long age, @RequestParam(required = false) String namePart, @RequestParam(required = false) Long minAge, @RequestParam(required = false) Long maxAge) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(studentService.findByName(name));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        if (namePart != null && !namePart.isBlank()) {
            return ResponseEntity.ok(studentService.findBySymbol(namePart));
        }
        if (name != null && !name.isBlank() && age != null) {
            return ResponseEntity.ok(studentService.findByNameAndAge(name, age));
        }
        if (minAge != null && maxAge != null) {
            return ResponseEntity.ok(studentService.findByAgeInBetween(minAge, maxAge));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
