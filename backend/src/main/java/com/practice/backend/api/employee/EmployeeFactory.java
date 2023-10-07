package com.practice.backend.api.employee;

import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;
import com.practice.backend.api.employee.model.Hobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeFactory {

    private final HobbyRepository hobbyRepository;

    public Employee DtoToEntity(EmployeeDto dto) {
        Employee employee = Employee.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .birthday(dto.getBirthday())
                .hobbies(new ArrayList<>())
                .build();
        if (dto.getHobbies() != null) {
            for (String hobbyName : dto.getHobbies()) {
                Optional<Hobby> hobby = hobbyRepository.findByName(hobbyName);
                employee.addHobby(hobby.orElse(new Hobby(hobbyName)));
            }
        }
        return employee;
    }
}

