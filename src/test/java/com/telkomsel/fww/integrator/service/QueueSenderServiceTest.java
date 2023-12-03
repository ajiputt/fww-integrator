package com.telkomsel.fww.integrator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class QueueSenderServiceTest {

    QueueSenderService queueSenderService;

    @Mock
    private AmqpTemplate rabbitTemplate;

    @BeforeEach
    void init() {
        queueSenderService = new QueueSenderService(rabbitTemplate);
    }

    @Test
    void sendQueue() {
        ReflectionTestUtils.setField(queueSenderService, "emailQueueName",
                "queue");
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());
        queueSenderService.sendQueue("booking");

        Assertions.assertThat(queueSenderService.emailQueueName).isNotNull();
    }
}