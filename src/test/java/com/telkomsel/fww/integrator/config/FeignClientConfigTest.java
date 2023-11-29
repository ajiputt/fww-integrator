package com.telkomsel.fww.integrator.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeignClientConfigTest {

    FeignClientConfig feignClientConfig;

    FeignClientConfig feignClientConfig2;

    @BeforeEach
    void init() {
        feignClientConfig = new FeignClientConfig();
        feignClientConfig2 = new FeignClientConfig("test", "test");
    }

    @Test
    void basicAuthRequestInterceptor() {
        BasicAuthRequestInterceptor resp =
                feignClientConfig2.basicAuthRequestInterceptor();
        Assertions.assertThat(resp).isNotNull();
    }
}