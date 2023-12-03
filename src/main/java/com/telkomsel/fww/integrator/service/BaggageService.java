package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Baggage;
import com.telkomsel.fww.integrator.feign.service.BaggageClientService;
import com.telkomsel.fww.integrator.payload.request.RequestCheckin;
import org.springframework.stereotype.Service;

@Service
public class BaggageService {

    private final BaggageClientService baggageClientService;

    public BaggageService(BaggageClientService baggageClientService) {
        this.baggageClientService = baggageClientService;
    }

    public Baggage postBaggage(RequestCheckin requestCheckin) {
        return baggageClientService.postBaggage(Baggage.builder()
                .code("BAG-" + requestCheckin.getBookingCode())
                .bookingCode(requestCheckin.getBookingCode())
                .weight(requestCheckin.getWeight())
                .description(requestCheckin.getDescription()).build());
    }


}
