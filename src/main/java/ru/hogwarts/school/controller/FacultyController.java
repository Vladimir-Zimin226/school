package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        facultyService.createFaculty(faculty);
        return new ResponseEntity<>(faculty, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public Faculty editeFaculty(@RequestBody Faculty faculty, @PathVariable Long id) {
        return facultyService.editFaculty(faculty, id);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/filter/{color}")
    public List<Faculty> getFaculties(@PathVariable String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/filter")
    public List<Faculty> getFacultiesByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        return facultyService.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }
}
