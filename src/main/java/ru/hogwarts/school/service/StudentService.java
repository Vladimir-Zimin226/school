package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    public Student createStudent(Student student);

    public Student findStudent(long id);

    public Student editStudent(Student student);

    public Student deleteStudent(long id);

    public Collection<Student> getAllStudents();

    List<Student> ageFilter(int age);
}
