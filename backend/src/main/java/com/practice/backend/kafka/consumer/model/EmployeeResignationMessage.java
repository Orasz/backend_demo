package com.practice.backend.kafka.consumer.model;

import lombok.Data;

@Data
public class EmployeeResignationMessage {
    private String eventId;
    private String employeeId;
    private String terminationRequestedAt;
    private String lastDayAtWork;
    private String reason;
}
