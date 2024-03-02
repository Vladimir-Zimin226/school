package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface FacultyService {

    public Faculty createFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    public Faculty editFaculty(Faculty faculty);

    public Faculty deleteFaculty(long id);

    public Collection<Faculty> getAllFaculties();

    List<Faculty> colorFilter(String color);
}
