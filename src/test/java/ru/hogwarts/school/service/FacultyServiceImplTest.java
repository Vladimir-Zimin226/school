package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FacultyServiceImplTest {

    // Create instance of FacultyServiceImpl
    private FacultyServiceImpl facultyService = new FacultyServiceImpl();

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty(null, "Math", "Blue");
        Faculty createdFaculty = facultyService.createFaculty(faculty);

        assertNotNull(createdFaculty);
        assertNotNull(createdFaculty.getId());
        assertEquals(faculty.getName(), createdFaculty.getName());
        assertEquals(faculty.getColor(), createdFaculty.getColor());
    }

    @Test
    public void testFindFaculty() {
        Faculty faculty = new Faculty(null, "Physics", "Green");
        Faculty createdFaculty = facultyService.createFaculty(faculty);

        Long facultyId = createdFaculty.getId();

        Faculty foundFaculty = facultyService.findFaculty(facultyId);

        assertNotNull(foundFaculty);
        assertEquals(createdFaculty, foundFaculty);
    }

    @Test
    public void testEditFaculty() {
        Faculty faculty = new Faculty(null, "Chemistry", "Red");
        Faculty createdFaculty = facultyService.createFaculty(faculty);

        createdFaculty.setName("Biology");
        Faculty editedFaculty = facultyService.editFaculty(createdFaculty);

        assertEquals("Biology", editedFaculty.getName());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty(null, "History", "Yellow");
        Faculty createdFaculty = facultyService.createFaculty(faculty);

        Long facultyId = createdFaculty.getId();

        Faculty deletedFaculty = facultyService.deleteFaculty(facultyId);

        assertNotNull(deletedFaculty);
        assertEquals(createdFaculty, deletedFaculty);

        Faculty facultyNotFound = facultyService.findFaculty(facultyId);
        assertNull(facultyNotFound);
    }

    @Test
    public void testColorFilter() {
        Faculty faculty1 = new Faculty(null, "Literature", "Blue");
        Faculty faculty2 = new Faculty(null, "Art", "Red");
        Faculty faculty3 = new Faculty(null, "Music", "Blue");
        facultyService.createFaculty(faculty1);
        facultyService.createFaculty(faculty2);
        facultyService.createFaculty(faculty3);

        List<Faculty> blueFaculties = facultyService.colorFilter("Blue");
        assertEquals(2, blueFaculties.size());

        List<Faculty> redFaculties = facultyService.colorFilter("Red");
        assertEquals(1, redFaculties.size());
    }
}