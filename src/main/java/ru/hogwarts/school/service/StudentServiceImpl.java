package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student editStudent(Long id, Student student) {
        studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findByName(String name) {
        return studentRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Collection<Student> findByAge(Long age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findBySymbol(String part) {
        return studentRepository.findAllByNameContainsIgnoreCase(part);
    }

    @Override
    public Collection<Student> findByNameAndAge(String name, Long age) {
        return studentRepository.findStudentsByNameIgnoreCaseAndAge(name, age);
    }

    @Override
    public Collection<Student> findByAgeInBetween(Long minAge, Long maxAge) {
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

}
