package com.telkomsel.fww.integrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkomsel.fww.integrator.domain.Passenger;
import com.telkomsel.fww.integrator.payload.request.RequestPassenger;
import com.telkomsel.fww.integrator.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(value = PassengerController.class, excludeAutoConfiguration =
        {SecurityAutoConfiguration.class})
class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PassengerService passengerService;

    @Test
    void postPassenger() throws Exception {

        RequestPassenger requestPassenger = RequestPassenger.builder()
                .fullName("fullname")
                .nik("123456789")
                .title("Mr").build();

        when(passengerService.postPassenger(any())).thenReturn(Passenger.builder()
                .fullName("fullname")
                .title("Mr")
                .nik("123456789").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/passengers")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPassenger)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data" +
                        ".full_name").value("fullname"));
    }
}