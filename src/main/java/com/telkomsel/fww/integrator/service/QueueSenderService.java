package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.common.DetailEmailQueue;
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


    public void sendQueue(String bookingCode, String action) {
        rabbitTemplate.convertAndSend(emailQueueName,
                DetailEmailQueue.builder().bookingCode(bookingCode).action(action).build());
    }
}
