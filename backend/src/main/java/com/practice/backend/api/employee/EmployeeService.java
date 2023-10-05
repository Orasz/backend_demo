package com.practice.backend.api.employee;

import com.practice.backend.api.employee.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    public Employee findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Employee> findAll(){
        return repository.findAll();
    }

    public void deleteEmployee(long id){
        repository.deleteById(id);
    }

    public Employee updateEmployee(Employee updatedEmp){
        return  repository.save(updatedEmp);
    }


}
