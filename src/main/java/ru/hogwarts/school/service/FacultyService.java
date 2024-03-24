package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {

    public Faculty createFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    public Faculty editFaculty(Faculty faculty, Long id);

    public void deleteFaculty(long id);

    public Collection<Faculty> getAllFaculties();

    List<Faculty> getFacultiesByColor(String color);

    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);

    Collection<Student> getStudentByFaculty(Long facultyId);
}
