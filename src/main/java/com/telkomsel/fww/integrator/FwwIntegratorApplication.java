package com.telkomsel.fww.integrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FwwIntegratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FwwIntegratorApplication.class, args);
    }
}
