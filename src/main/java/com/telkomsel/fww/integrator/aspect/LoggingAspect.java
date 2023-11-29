package com.telkomsel.fww.integrator.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }


    @Around("@annotation(LogController)")
    public Object aroundControllerMethod(ProceedingJoinPoint jp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Object[] args = jp.getArgs();
        log.info("Request Path {}, Body {}", request.getRequestURI(),
                mapper.writeValueAsString(args));

        Object object = jp.proceed();

        log.info("Response Path {}, Body {}", request.getRequestURI(),
                mapper.writeValueAsString(object));
        return object;


    }
}
