package com.telkomsel.fww.integrator.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QueueConsumerService {

    private final EmailService emailService;

    public QueueConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${rabbitmq.email.queue}")
    public void receivedEmailMessage(String bookingCode) throws MessagingException {
        emailService.sendEmail(bookingCode);
    }
}
