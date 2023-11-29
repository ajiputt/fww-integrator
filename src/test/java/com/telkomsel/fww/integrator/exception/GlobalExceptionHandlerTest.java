package com.telkomsel.fww.integrator.exception;

import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.common.ResponseError;
import feign.RetryableException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private RetryableException retryableException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private UnsatisfiedServletRequestParameterException unsatisfiedServletRequestParameterException;

    @Mock
    private HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException;

    @Mock
    private RegisterException registerException;

    @Mock
    private Exception exception;


    @BeforeEach
    void init() {
        globalExceptionHandler = new GlobalExceptionHandler();

    }

    @Test
    void connectionTimeoutFeign() {
        ResponseEntity<Object> resp =
                globalExceptionHandler.connectionTimeoutFeign(retryableException);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleValidationError() {
        ResponseEntity<Object> resp = globalExceptionHandler.handleValidationError(methodArgumentNotValidException);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testHandleMissingParams() {
        ResponseEntity<Object> resp =
                globalExceptionHandler.handleMissingParams(unsatisfiedServletRequestParameterException);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMethodNotSupported() {
        ResponseEntity<Object> resp =
                globalExceptionHandler.handleMethodNotSupported(httpRequestMethodNotSupportedException);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void handleRegisterException() {
        ResponseEntity<Object> resp =
                globalExceptionHandler.handleRegisterException(registerException);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleAll() {
        ResponseEntity<Object> resp =
                globalExceptionHandler.handleAll(exception);
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void buildResponseEntity() {

        ResponseError apiError = new ResponseError(LocalDateTime.now(),
                HttpStatus.OK, "", "");

        ResponseEntity<Object> resp =
                globalExceptionHandler.buildResponseEntity(apiError, ResponseCode.SUCCESS);
        Assertions.assertThat(resp).isNotNull();
    }
}