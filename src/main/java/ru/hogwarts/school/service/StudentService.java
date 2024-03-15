package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    public Student createStudent(Student student);

    public Student findStudent(long id);

    Student editStudent(Long id, Student student);

    public void deleteStudent(long id);

    public Collection<Student> getAllStudents();

    Student findByName(String name);

    Collection<Student> findByAge(Long age);

    Collection<Student> findBySymbol(String part);

    Collection<Student> findByNameAndAge(String name, Long age);

    Collection<Student> findByAgeInBetween(Long minAge, Long maxAge);
}
