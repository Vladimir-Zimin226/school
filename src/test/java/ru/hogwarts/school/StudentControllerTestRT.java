package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRT {

    List<Student> savedStudents;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Student student = new Student(111L, "Harry", 12);
        Student student2 = new Student(222L, "Hermione", 13);
        List<Student> studentList = List.of(student, student2);

        savedStudents = studentRepository.saveAll(studentList);
    }

    @AfterEach
    void tearUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() throws JSONException, JsonProcessingException {
        String expected = mapper.writeValueAsString(savedStudents.get(0));

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/student/" + savedStudents.get(0).getId(),String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void testGetStudents() {
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Student> actualStudents = response.getBody().stream().collect(Collectors.toList());
        assertEquals(savedStudents, actualStudents);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setName("Cedric");
        student.setAge(16);
        Student actual = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        actual.setName("Godrich");
        actual.setAge(22);
        ResponseEntity<Student> updated = this.restTemplate.exchange
                ("http://localhost:" + port + "/student/" + actual.getId(), HttpMethod.PUT, new HttpEntity<>(actual), Student.class);

        Assertions.assertThat(updated.getBody().getId()).isNotNull();
        Assertions.assertThat(updated.getBody().getName()).isEqualTo("Godrich");
        Assertions.assertThat(updated.getBody().getAge()).isEqualTo(22);
    }

    @Test
    void createStudent() throws JSONException, JsonProcessingException{

        Student student = new Student(15L, "Draco", 16);
        String expected = mapper.writeValueAsString(student);

        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Optional<Student> actual = studentRepository.findById(response.getBody().getId());

        Assertions.assertThat(actual.get().getName()).isEqualTo("Draco");
        Assertions.assertThat(actual.get().getAge()).isEqualTo(16);
    }

    @Test
    void deleteStudent() {

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange("/student/" + savedStudents.get(0).getId(), HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
