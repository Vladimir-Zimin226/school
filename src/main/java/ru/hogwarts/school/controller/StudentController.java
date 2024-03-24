package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;
import java.util.Collection;

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

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> editeStudent(@RequestBody Student student, @PathVariable Long id) {
        Student foundStudent = studentService.editStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
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

    @GetMapping("/{id}/faculties")
    public Faculty getFacultyByStudentId(@RequestParam(required = false) Long studentId) {
        return studentService.getFacultyByStudentId(studentId);

    }
}
