package com.practice.backend.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "kafka-topic", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}