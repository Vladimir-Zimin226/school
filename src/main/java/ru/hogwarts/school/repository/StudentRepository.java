package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByNameIgnoreCase(String name);

    Collection<Student> findByAge(Long age);

    Collection<Student> findAllByNameContainsIgnoreCase(String part);

    Collection<Student> findStudentsByNameIgnoreCaseAndAge(String name, Long age);

    Collection<Student> findStudentsByAgeBetween(Long minAge, Long maxAge);

}
