package com.practice.backend.Employee;

import com.practice.backend.EmployeeApplication;
import com.practice.backend.api.ApiSecurity;
import com.practice.backend.api.employee.EmployeeController;
import com.practice.backend.api.employee.EmployeeFactory;
import com.practice.backend.api.employee.EmployeeService;
import com.practice.backend.api.employee.dto.EmployeeDto;
import com.practice.backend.api.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.practice.backend.Employee.TestUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes={EmployeeApplication.class, ApiSecurity.class})

public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeFactory employeeFactory;

    @Test
    void getEmployee_shouldReturn404() throws Exception {
        var employeeId = 1;

        when(employeeService.findById(employeeId)).thenReturn(null);
        mvc.perform(get("/v1/api/employee/" + employeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEmployee_shouldReturn200() throws Exception {
        var employeeId = 1L;
        Employee emp = TestUtils.createValidEmployee(employeeId);

        when(employeeService.findById(employeeId)).thenReturn(emp);

        mvc.perform(get("/v1/api/employee/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.fullName",is(emp.getFullName())))
                .andExpect(jsonPath("$.birthday",is(String.valueOf(emp.getBirthday()))))
                .andExpect(jsonPath("$.email",is(emp.getEmail())));
    }

    @Test
    void postEmployeeWithWrongEmail_shouldReturn400() throws Exception {
        EmployeeDto dto = TestUtils.createValidEmployeeDto();
        dto.setEmail("shouldNotPass");
        mvc.perform(post("/v1/api/employee")
                        .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void postEmployee_shouldReturnOk() throws Exception {
        EmployeeDto dto = TestUtils.createValidEmployeeDto();
        mvc.perform(post("/v1/api/employee")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void putEmployee_shouldReturn404() throws Exception {
        EmployeeDto dto = TestUtils.createValidEmployeeDto();

        var employeeId = 1L;

        when(employeeService.findById(employeeId)).thenReturn(null);

        mvc.perform(put("/v1/api/employee/" + employeeId)
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void putEmployee_shouldReturn200() throws Exception {
        EmployeeDto dto = TestUtils.createValidEmployeeDto();
        var employeeId = 1L;
        Employee emp = TestUtils.createValidEmployee(employeeId);

        when(employeeService.findById(employeeId)).thenReturn(emp);

        mvc.perform(post("/v1/api/employee")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
