package com.practice.backend.Employee.integration;

import com.practice.backend.Employee.TestUtils;
import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;
import com.practice.backend.api.employee.repository.EmployeeRepository;
import com.practice.backend.api.employee.repository.HobbyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeApiIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @LocalServerPort
    private Integer port;

    HttpHeaders headers = new HttpHeaders();


    @Test
    void testCreateAndDeleteEmployee() {
        EmployeeDto dto = TestUtils.createValidEmployeeDto();
        List<String> hobbies = new ArrayList<>();
        hobbies.add("tennis");
        dto.setHobbies(hobbies);

        HttpEntity<EmployeeDto> entity = new HttpEntity<>(dto, headers);

         testRestTemplate.exchange(
                createURLWithPort("/v1/api/employee"), HttpMethod.POST, entity, Employee.class);

        assertEquals(1, employeeRepository.count());
        assertEquals(1, hobbyRepository.count());

        long empId = employeeRepository.findAll().get(0).getId();

        testRestTemplate.exchange(
                createURLWithPort("/v1/api/employee/" + empId), HttpMethod.DELETE, null, Employee.class);

        assertEquals(0, employeeRepository.count());
        assertEquals(1, hobbyRepository.count());

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
