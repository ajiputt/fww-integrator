package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.service.AirportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(value = AirportController.class, excludeAutoConfiguration =
        {SecurityAutoConfiguration.class})
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;


    @Test
    void getAirports() throws Exception {

        List<Airport> resp =
                Arrays.asList(Airport.builder().name("test name").build());

        when(airportService.getAirport()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/airports")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(
                        "test name"));
    }
}