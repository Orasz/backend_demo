package com.practice.backend.kafka.consumer;

import com.practice.backend.api.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {


    private final EmployeeService service;

    @Autowired
    public MessageConsumer(EmployeeService service) {
        this.service = service;
    }

    @KafkaListener(topics = "employee-events", groupId = "group-id")
    public void listen(String message) {
        log.info("Received message: " + message);
        service.readResignationMessage(message);
    }

}