package com.practice.backend.api.employee;

import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;

public class EmployeeFactory {

    public static Employee DtoToEntity(EmployeeDto dto){
        return Employee.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .birthday(dto.getBirthday())
                .build();
    }

}
