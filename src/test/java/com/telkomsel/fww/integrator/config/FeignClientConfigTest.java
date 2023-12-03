package com.telkomsel.fww.integrator.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class FeignClientConfigTest {

    FeignClientConfig feignClientConfig;

    @BeforeEach
    void init() {
        feignClientConfig = new FeignClientConfig();

        ReflectionTestUtils.setField(feignClientConfig, "username", "test");
        ReflectionTestUtils.setField(feignClientConfig, "password", "test");
    }

    @Test
    void basicAuthRequestInterceptor() {
        BasicAuthRequestInterceptor resp =
                feignClientConfig.basicAuthRequestInterceptor();
        Assertions.assertThat(resp).isNotNull();
    }
}