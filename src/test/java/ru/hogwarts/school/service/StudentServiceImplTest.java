package ru.hogwarts.school.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        assertEquals(student, studentService.createStudent(new Student()));
    }

    @Test
    public void testFindStudent() {
        Student student = new Student();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        assertEquals(student, studentService.findStudent(1L));
    }

    @Test
    public void testEditStudent() {
        Student student = new Student();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        assertEquals(student, studentService.editStudent(1L, new Student()));
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentRepository.findAll()).thenReturn(students);

        assertEquals(students, studentService.getAllStudents());
    }
}