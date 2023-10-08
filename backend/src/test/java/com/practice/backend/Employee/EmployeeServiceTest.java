package com.practice.backend.Employee;

import com.practice.backend.api.employee.EmployeeService;
import com.practice.backend.api.employee.repository.EmployeeRepository;
import com.practice.backend.kafka.producer.MessageProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private  EmployeeRepository repository;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private EmployeeService service;

    //some methods are not tested as there's no custom logic.

    @Test
    void put_shouldUpdateEmployee(){
        var employee = TestUtils.createValidEmployee(1L);

        var newEmp = TestUtils.createValidEmployee(1L);
        newEmp.setEmail("test the change");


        when(repository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(repository.save(any())).thenReturn(newEmp);

        var result = service.updateEmployee(newEmp ,1L);
        assertEquals(
                newEmp,
                result
        );
        assertEquals("test the change", result.getEmail());
    }

}
