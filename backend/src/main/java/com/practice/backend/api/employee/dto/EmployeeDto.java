package com.practice.backend.api.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDto {

    @Email(message = "email is mandatory.")
    private String email;

    @NotBlank(message = "name is mandatory.")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    List<String> hobbies;
}
