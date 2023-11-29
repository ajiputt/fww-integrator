package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.feign.response.AirportEmbeddedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "airports", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface AirportClientService {

    @GetMapping("/airports?projection=airport-view")
    AirportEmbeddedResponse getAirports();

}
