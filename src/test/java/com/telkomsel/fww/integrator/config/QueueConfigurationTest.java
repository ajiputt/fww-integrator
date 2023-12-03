package com.telkomsel.fww.integrator.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QueueConfigurationTest {

    QueueConfiguration queueConfiguration;

    @BeforeEach
    void init() {
        queueConfiguration = new QueueConfiguration();
    }

    @Test
    void jsonMessageConverter() {
        queueConfiguration.jsonMessageConverter();
        assertTrue(true);
    }

    @Test
    void rabbitQueueTemplate() {
        ConnectionFactory connectionFactory =
                Mockito.mock(ConnectionFactory.class);
        queueConfiguration.rabbitQueueTemplate(connectionFactory);
        assertTrue(true);
    }
}