package com.nttdata.bootcamp.notification.web.controller;

import com.nttdata.bootcamp.notification.domain.dto.NotificationResponse;
import com.nttdata.bootcamp.notification.domain.dto.email.NotificationEmailRequest;
import com.nttdata.bootcamp.notification.domain.dto.email.NotificationEmailResponse;
import com.nttdata.bootcamp.notification.domain.service.EventProducer;
import io.netty.handler.codec.http2.Http2ChannelDuplexHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import lombok.Data;

@RestController
@RequestMapping(value = "/correo")
public class EmailController {
    private final EventProducer kafkaStringProducer;

    @Autowired
    EmailController(EventProducer kafkaStringProducer) {

        this.kafkaStringProducer = kafkaStringProducer;
    }

    @GetMapping(value = "/test")
    public Flux<String>    test() {

        return Flux.just("Servicio batch correo test ejecutando");
    }

    @PostMapping(value = "/publish")
    public ResponseEntity<NotificationResponse> sendMessageToKafkaTopic(@RequestBody NotificationEmailRequest request) {
        this.kafkaStringProducer.sendMessage(request);
        NotificationEmailResponse response=
                NotificationEmailResponse.builder()
                        .build();


        response.setCodResponse("0");
        response.setMessageResponse("Exitoso");

        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
