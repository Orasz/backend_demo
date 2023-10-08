package com.practice.backend.Employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;

import java.time.LocalDate;

public class TestUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static EmployeeDto createValidEmployeeDto(){
        return  EmployeeDto.builder()
                .fullName("Daniele Ventura")
                .birthday(LocalDate.parse("1996-02-19"))
                .email("test@email.it")
                .build();
    }

    public static Employee createValidEmployee(long id){
        return  Employee.builder()
                .id(id)
                .fullName("Daniele Ventura")
                .birthday(LocalDate.parse("1996-02-19"))
                .email("test@email.it")
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
