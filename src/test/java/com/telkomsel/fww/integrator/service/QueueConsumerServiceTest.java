package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.common.DetailEmailQueue;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueConsumerServiceTest {

    QueueConsumerService queueConsumerService;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void init() {
        queueConsumerService = new QueueConsumerService(emailService);
    }

    @Test
    void receivedEmailMessage() throws MessagingException {
        doNothing().when(emailService).sendEmail(isA(String.class), isA(String.class));

        queueConsumerService.receivedEmailMessage(DetailEmailQueue.builder().bookingCode("test").action("booking").build());

        verify(emailService, times(1)).sendEmail("test", "booking");
    }
}