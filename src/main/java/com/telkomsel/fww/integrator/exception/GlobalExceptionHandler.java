package com.telkomsel.fww.integrator.exception;

import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.common.ResponseError;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    public ResponseEntity<Object> buildResponseEntity(ResponseError apiError,
                                                      ResponseCode responseCode) {
        return ResponseHandler.generateResponse(responseCode,
                apiError.getStatus(), apiError);
    }


    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Object> connectionTimeoutFeign(RetryableException e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR, "Connection Error",
                e.getMessage()), ResponseCode.ERROR_CONNECTION_FEIGN);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Validation Error",
                e.getMessage()), ResponseCode.ERROR_VALIDATION);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(UnsatisfiedServletRequestParameterException e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Missing Parameter",
                e.getMessage()), ResponseCode.ERROR_PARAMETER);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(Exception e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED, "Method Not Supported",
                e.getMessage()), ResponseCode.ERROR_METHOD);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<Object> handleRegisterException(RegisterException e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Username or Email Already Exist",
                e.getMessage()), ResponseCode.ERROR_PARAMETER);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception e) {
        log.error(e.getMessage());
        return buildResponseEntity(new ResponseError(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                e.getMessage()), ResponseCode.ERROR_GENERAL);
    }

}
