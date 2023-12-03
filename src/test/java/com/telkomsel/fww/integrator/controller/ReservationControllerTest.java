package com.telkomsel.fww.integrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkomsel.fww.integrator.domain.Baggage;
import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.payload.request.RequestCheckin;
import com.telkomsel.fww.integrator.payload.request.RequestReservation;
import com.telkomsel.fww.integrator.service.BaggageService;
import com.telkomsel.fww.integrator.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(value = ReservationController.class, excludeAutoConfiguration =
        {SecurityAutoConfiguration.class})
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private BaggageService baggageService;


    @Test
    void postReservation() throws Exception {

        RequestReservation requestReservation =
                RequestReservation.builder()
                        .seatNo(1)
                        .nik("123456789")
                        .scheduleCode("SCHEDULE1")
                        .build();


        when(reservationService.postReservation(any(), any())).thenReturn(Reservation.builder()
                .seatNo(1)
                .nik("123456789")
                .scheduleCode("SCHEDULE1")
                .bookingCode("BOOK1").build());

        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/reservations")
                        .principal(mockPrincipal)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestReservation)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data" +
                        ".booking_code").value("BOOK1"));
    }

    @Test
    void getReservations() throws Exception {

        when(reservationService.getReservationByBookingCode(any())).thenReturn(Reservation.builder()
                .bookingCode("booking-code")
                .seatNo(1).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservations")
                        .param("booking", "booking-code")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.booking_code").value(
                        "booking-code"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.seat_no").value(
                        1));

    }

    @Test
    void getReservationsByUser() throws Exception {
        List<Reservation> resp =
                Arrays.asList(Reservation.builder().bookingCode("bookingcode1").build(),
                        Reservation.builder().bookingCode("bookingcode2").build());

        when(reservationService.getReservationByUser(any())).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservations")
                        .param("username", "username")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]" +
                        ".booking_code").value("bookingcode2"));
    }

    @Test
    void cancelReservation() throws Exception {
        when(reservationService.updateReservation(any(), any())).thenReturn(Reservation.builder()
                .bookingCode("booking-code")
                .status("C")
                .seatNo(1).build());

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/reservations/cancel/{booking" +
                                "-code}", "booking-code")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.booking_code").value(
                        "booking-code"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(
                        "C"));
    }

    @Test
    void callbackSuccessReservation() throws Exception {
        when(reservationService.updateReservation(any(), any())).thenReturn(Reservation.builder()
                .bookingCode("booking-code")
                .status("P")
                .seatNo(1).build());

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/reservations/cancel/{booking" +
                                "-code}", "booking-code")
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.booking_code").value(
                        "booking-code"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(
                        "P"));
    }

    @Test
    void updateCheckInReservation() throws Exception {
        RequestCheckin requestCheckin = RequestCheckin.builder()
                .bookingCode("booking-code")
                .weight(new BigDecimal(1)).build();
        when(baggageService.postBaggage(any())).thenReturn(Baggage.builder()
                .bookingCode("booking-code")
                .code("baggageCode").build());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/reservations/checkin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCheckin))
                        .with(SecurityMockMvcRequestPostProcessors.user("username")
                                .password("pass")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.booking_code").value(
                        "booking-code"));
    }
}