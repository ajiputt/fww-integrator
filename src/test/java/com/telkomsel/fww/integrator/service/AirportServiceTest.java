package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.feign.response.AirportEmbeddedResponse;
import com.telkomsel.fww.integrator.feign.service.AirportClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    AirportService airportService;

    @Mock
    private AirportClientService airportClientService;

    @BeforeEach
    void init() {
        airportService = new AirportService(airportClientService);
    }

    @Test
    void getAirport() {
        List<Airport> airportList =
                Arrays.asList(Airport.builder().code("A").name("Name A").city("City A").build(),
                        Airport.builder().code("B").name("Name B").city("City B").build());
        AirportEmbeddedResponse.Embedded embedded =
                new AirportEmbeddedResponse.Embedded();
        embedded.setAirports(airportList);
        AirportEmbeddedResponse expected =
                AirportEmbeddedResponse.builder()
                        .embedded(embedded)
                        .build();


        when(airportClientService.getAirports()).thenReturn(expected);
        List<Airport> res = airportService.getAirport();
        Assertions.assertThat(res).hasSize(2);
        Assertions.assertThat(res.get(0).getName()).isEqualTo("Name A");
        Assertions.assertThat(res.get(1).getCity()).isEqualTo("City B");
    }
}