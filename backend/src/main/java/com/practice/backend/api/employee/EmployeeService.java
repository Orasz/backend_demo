package com.practice.backend.api.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.backend.api.employee.model.Employee;
import com.practice.backend.api.employee.repository.EmployeeRepository;
import com.practice.backend.kafka.consumer.model.EmployeeResignationMessage;
import com.practice.backend.kafka.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository repository;

    private final MessageProducer messageProducer;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public Employee findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public void deleteEmployee(long id) {
        repository.deleteById(id);
        messageProducer.sendMessage("delete_topic", String.valueOf(id));
    }

    public Employee updateEmployee(Employee updatedEmp, long id) {
        Employee emp = repository.findById(id)
                .map(employee -> {
                    employee.setEmail(updatedEmp.getEmail());
                    employee.setHobbies(updatedEmp.getHobbies());
                    return repository.save(employee);
                }).
                orElse(null);
        try {
            String empString = objectMapper.writeValueAsString(emp);
            messageProducer.sendMessage("update_topic", empString);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Error while serializing message. ex= {}", jsonProcessingException.getMessage());
            handleMessageNotSent(emp);
        }
        return emp;
    }


    public Employee create(Employee employee) {
        Employee emp = repository.save(employee);
        try {
            String empString = objectMapper.writeValueAsString(emp);
            messageProducer.sendMessage("create_topic", empString);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Error while serializing message. ex= {}", jsonProcessingException.getMessage());
            handleMessageNotSent(emp);
        }
        return emp;
    }

    private void handleMessageNotSent(Object emp) {
        //Do something here
    }

    public void readResignationMessage(String message) {
        try {
            EmployeeResignationMessage receivedMessage = objectMapper.readValue(message, EmployeeResignationMessage.class);
            deleteEmployee(Long.parseLong(receivedMessage.getEmployeeId()));
        }
        catch (JsonProcessingException jsonProcessingException){
            log.error("Impossible to process the received message. topic: kafka_topic.");
        }
    }

    public void writeResignationMessageOnTopic(EmployeeResignationMessage message) {
        try {
            String resignationMessageString = objectMapper.writeValueAsString(message);
            messageProducer.sendMessage("employee-events", resignationMessageString);
            log.info("Employee with id {} correctly deleted.", message.getEmployeeId());
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Error while serializing message. ex= {}", jsonProcessingException.getMessage());
            handleMessageNotSent(message);
        }
    }
}
