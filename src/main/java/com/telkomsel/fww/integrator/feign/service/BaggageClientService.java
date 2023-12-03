package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.Baggage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "baggages", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface BaggageClientService {

    @PostMapping("/baggages")
    Baggage postBaggage(@RequestBody Baggage baggage);

}
