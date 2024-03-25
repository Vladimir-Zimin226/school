package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRT {

    List<Faculty> savedFaculties;
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Faculty faculty = new Faculty(111L, "Slitherin", "green");
        Faculty faculty2 = new Faculty(222L, "Puffenduy", "yellow");
        List<Faculty> facultyList = List.of(faculty, faculty2);

        savedFaculties = facultyRepository.saveAll(facultyList);
    }

    @AfterEach
    void tearUp() {
        facultyRepository.deleteAll();
    }

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Kogtewran");
        faculty.setColor("Brown");
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Assertions.assertThat(actual.getId()).isNotNull();
        Assertions.assertThat(actual.getName()).isEqualTo("Kogtewran");
        Assertions.assertThat(actual.getColor()).isEqualTo("Brown");
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Slith");
        faculty.setColor("Green");
        Faculty actual = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        actual.setName("SlitherinTest");
        actual.setColor("GreenTest");
        ResponseEntity<Faculty> updated = this.restTemplate.exchange
                ("http://localhost:" + port + "/faculty/" + actual.getId(), HttpMethod.PUT, new HttpEntity<>(actual), Faculty.class);
        Assertions.assertThat(updated.getBody().getId()).isNotNull();
        Assertions.assertThat(updated.getBody().getName()).isEqualTo("SlitherinTest");
        Assertions.assertThat(updated.getBody().getColor()).isEqualTo("GreenTest");
    }

    @Test
    void testGetFaculties() {
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Faculty> actualFaculty = response.getBody().stream().collect(Collectors.toList());
        assertEquals(savedFaculties, actualFaculty);
    }

    @Test
    void deleteFaculty() {

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange("/faculty/" + savedFaculties.get(0).getId(), HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
