package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.common.DetailEmailQueue;
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
    public void receivedEmailMessage(DetailEmailQueue
                                             detailEmailQueue) throws MessagingException {
        emailService.sendEmail(detailEmailQueue.getBookingCode(), detailEmailQueue.getAction());
    }
}
