package com.telkomsel.fww.integrator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    LoggingAspect loggingAspect;

    @Mock
    ProceedingJoinPoint jp;

    private MockHttpServletRequest mockRequest;

    @BeforeEach
    void init() {
        mockRequest = new MockHttpServletRequest();
        loggingAspect = new LoggingAspect(mockRequest);
    }

    @Test
    void aroundControllerMethod() throws Throwable {
        loggingAspect.aroundControllerMethod(jp);
        Assertions.assertThat(true);
    }
}