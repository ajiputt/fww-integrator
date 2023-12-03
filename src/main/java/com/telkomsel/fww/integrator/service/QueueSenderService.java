package com.telkomsel.fww.integrator.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueSenderService {

    @Value("${rabbitmq.email.queue}")
    String emailQueueName;

    private final AmqpTemplate rabbitTemplate;

    public QueueSenderService(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendQueue(String bookingCode) {
        rabbitTemplate.convertAndSend(emailQueueName, bookingCode);
    }
}
