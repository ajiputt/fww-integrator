package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Passenger;
import com.telkomsel.fww.integrator.feign.service.PassengerClientService;
import com.telkomsel.fww.integrator.payload.request.RequestPassenger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    PassengerService passengerService;

    @Mock
    private HttpExternalService httpExternalService;

    @Mock
    private PassengerClientService passengerClientService;

    @BeforeEach
    void init() {
        passengerService = new PassengerService(httpExternalService, passengerClientService);
    }

    @Test
    void postPassenger() {
        Passenger passenger = Passenger.builder()
                .fullName("test")
                .nik("12321313")
                .title("Mr")
                .createdAt(LocalDateTime.now()).build();

        when(passengerClientService.postPassenger(any())).thenReturn(passenger);

        Passenger resp = passengerService.postPassenger(RequestPassenger.builder()
                .fullName("test")
                .nik("12321313")
                .title("Mr").build());

        Assertions.assertThat(resp.getFullName()).isEqualTo("test");
    }
}