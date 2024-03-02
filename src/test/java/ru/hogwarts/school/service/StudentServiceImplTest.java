package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceImplTest {

    // Create instance of StudentServiceImpl
    private StudentServiceImpl studentService = new StudentServiceImpl();

    @Test
    public void testCreateStudent() {
        Student student = new Student(null, "Alice", 20);
        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertNotNull(createdStudent.getId());
        assertEquals(student.getName(), createdStudent.getName());
        assertEquals(student.getAge(), createdStudent.getAge());
    }

    @Test
    public void testFindStudent() {
        Student student = new Student(null, "Bob", 25);
        Student createdStudent = studentService.createStudent(student);

        Long studentId = createdStudent.getId();

        Student foundStudent = studentService.findStudent(studentId);

        assertNotNull(foundStudent);
        assertEquals(createdStudent, foundStudent);
    }

    @Test
    public void testEditStudent() {
        Student student = new Student(null, "Charlie", 30);
        Student createdStudent = studentService.createStudent(student);

        createdStudent.setAge(35);
        Student editedStudent = studentService.editStudent(createdStudent);

        assertEquals(35, editedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(null, "David", 22);
        Student createdStudent = studentService.createStudent(student);

        Long studentId = createdStudent.getId();

        Student deletedStudent = studentService.deleteStudent(studentId);

        assertNotNull(deletedStudent);
        assertEquals(createdStudent, deletedStudent);

        Student studentNotFound = studentService.findStudent(studentId);
        assertNull(studentNotFound);
    }

    @Test
    public void testAgeFilter() {
        Student student1 = new Student(null, "Eve", 20);
        Student student2 = new Student(null, "Frank", 25);
        Student student3 = new Student(null, "Grace", 20);
        studentService.createStudent(student1);
        studentService.createStudent(student2);
        studentService.createStudent(student3);

        List<Student> age20Students = studentService.ageFilter(20);
        assertEquals(2, age20Students.size());

        List<Student> age25Students = studentService.ageFilter(25);
        assertEquals(1, age25Students.size());
    }
}
