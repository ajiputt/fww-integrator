package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.Passenger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "passengers", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface PassengerClientService {

    @PostMapping("/passengers")
    Passenger postPassenger(@RequestBody Passenger passenger);
}
