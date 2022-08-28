package com.nttdata.bootcamp.notification.domain.service;

import com.google.gson.Gson;
import com.nttdata.bootcamp.notification.domain.dto.email.NotificationEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventProducer(@Qualifier("kafkaStringTemplate") KafkaTemplate<String,
                                String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationEmailRequest request) {
        Gson gson = new Gson();
        LOGGER.info("Producing message {}", "Mensaje enviado");


        this.kafkaTemplate.send("TOPIC-DEMO", gson.toJson(request));

    }

}