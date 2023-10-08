package com.practice.backend.api.employee;

import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;
import com.practice.backend.kafka.consumer.model.EmployeeResignationMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeFactory employeeFactory;


    @Operation(
            summary = "Retrieve an Employee by Id",
            description = "Get an employee object by specifying its id. The response is an Employee with name, email, birthday and hobbies.",
            tags = { "employee", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getAllEmployees(@PathVariable("id")long id){
        Employee retrievedEmployee = employeeService.findById(id);
        if(retrievedEmployee == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(retrievedEmployee);
    }

    @Operation(
            summary = "Retrieve all the employees",
            description = "Retrieve all the employees with all their attributes.",
            tags = { "employees", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
     })
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> retrievedEmployees = employeeService.findAll();
        return new ResponseEntity<>(retrievedEmployees, HttpStatus.OK);
    }

    @Operation(
            summary = "Create an Employee",
            description = "Create an employee by specifying its attributes. The response is the created entity.",
            tags = { "employee", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping ("/employee")
    @Transactional
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeFactory.DtoToEntity(employeeDto);
        Employee createdEmployee = employeeService.create(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an Employee",
            description = "Update an employee's email or hobbies. The response is the whole employee entity.",
            tags = { "employee", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping ("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody  EmployeeDto employeeDto, @PathVariable long id){
        Employee employee = employeeFactory.DtoToEntity(employeeDto);
        Employee retrievedEmployee = employeeService.updateEmployee(employee, id);
        if(retrievedEmployee == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(retrievedEmployee, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete an Employee",
            description = "delete an employee by specifying it id.",
            tags = { "employee", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/employee/topic")
    public ResponseEntity<?> WriteMessageOnResignationTopic(@RequestBody EmployeeResignationMessage message){
        employeeService.writeResignationMessageOnTopic(message);
        return ResponseEntity.ok(null);
    }


}
