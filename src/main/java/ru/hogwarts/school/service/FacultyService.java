package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {

    public Faculty createFaculty(Faculty faculty);

    public Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty, Long id);

    public void deleteFaculty(long id);

    public Collection<Faculty> getAllFaculties();

}
