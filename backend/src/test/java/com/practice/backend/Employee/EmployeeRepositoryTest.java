package com.practice.backend.Employee;

import com.practice.backend.api.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void findById_shouldGetEmployee(){
        var empToSave = TestUtils.createValidEmployee(1L);

        var expectedEmp = repository.save(empToSave);

        assertEquals(
                Optional.of(expectedEmp),
                repository.findById(1L)
        );
    }

}
