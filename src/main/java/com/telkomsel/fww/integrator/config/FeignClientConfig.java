package com.telkomsel.fww.integrator.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${fww.core.username}")
    private String username;

    @Value("${fww.core.password}")
    private String password;

    public FeignClientConfig() {

    }

    public FeignClientConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bean
    BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}
