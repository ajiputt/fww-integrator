package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Baggage;
import com.telkomsel.fww.integrator.feign.service.BaggageClientService;
import com.telkomsel.fww.integrator.payload.request.RequestCheckin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaggageServiceTest {

    BaggageService baggageService;

    @Mock
    private BaggageClientService baggageClientService;

    @BeforeEach
    void init() {
        baggageService = new BaggageService(baggageClientService);
    }

    @Test
    void postBaggage() {
        Baggage baggage = Baggage.builder()
                .bookingCode("booking").build();

        when(baggageClientService.postBaggage(any())).thenReturn(baggage);

        Baggage resp =
                baggageService.postBaggage(RequestCheckin.builder().build());

        Assertions.assertThat(resp.getBookingCode()).isEqualTo("booking");
    }
}