package ru.hogwarts.school.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty();
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        assertEquals(faculty, facultyService.createFaculty(new Faculty()));
    }

    @Test
    public void testFindFaculty() {
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        assertEquals(faculty, facultyService.findFaculty(1L));
    }

    @Test
    public void testEditFaculty() {
        Faculty faculty = new Faculty();
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        assertEquals(faculty, facultyService.editFaculty(new Faculty(), 1L));
    }

    @Test
    public void testDeleteFaculty() {
        doNothing().when(facultyRepository).deleteById(1L);

        facultyService.deleteFaculty(1L);
    }

    @Test
    public void testGetAllFaculties() {
        List<Faculty> faculties = Arrays.asList(new Faculty(), new Faculty());
        when(facultyRepository.findAll()).thenReturn(faculties);

        assertEquals(faculties, facultyService.getAllFaculties());
    }
}