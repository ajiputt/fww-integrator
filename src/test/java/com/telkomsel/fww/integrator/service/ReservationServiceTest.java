package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.feign.response.ReservationEmbeddedResponse;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    ReservationService reservationService;

    @Mock
    private ReservationClientService reservationClientService;

    @BeforeEach
    void init() {
        reservationService = new ReservationService(reservationClientService);
    }

    @Test
    void postReservation() {
        Reservation request = Reservation.builder()
                .bookingCode("testCode001")
                .createdAt(LocalDateTime.now())
                .nik("12345678")
                .scheduleCode("schedule001")
                .status("B").build();
        when(reservationClientService.postReservation(any(Reservation.class)))
                .thenReturn(request);
        Reservation resp =
                reservationService.postReservation(request);

        assertEquals(request.getBookingCode(), resp.getBookingCode());
    }

    @Test
    void getReservationByUser() {
        String username = "username";
        List<Reservation> reservationList =
                Arrays.asList(Reservation.builder()
                                .bookingCode("testCode001")
                                .createdAt(LocalDateTime.now())
                                .nik("12345678")
                                .scheduleCode("schedule001")
                                .status("B")
                                .createdBy("username").build(),
                        Reservation.builder()
                                .bookingCode("testCode002")
                                .createdAt(LocalDateTime.now())
                                .nik("12345678")
                                .scheduleCode("schedule002")
                                .status("B")
                                .createdBy("username").build());
        ReservationEmbeddedResponse.Embedded embedded =
                new ReservationEmbeddedResponse.Embedded();
        embedded.setReservations(reservationList);
        ReservationEmbeddedResponse expected =
                ReservationEmbeddedResponse.builder()
                        .embedded(embedded)
                        .build();
        when(reservationClientService.getReservationByUser(any(String.class))).thenReturn(expected);

        List<Reservation> resp =
                reservationService.getReservationByUser(username);

        Assertions.assertThat(resp).hasSize(2);
        Assertions.assertThat(resp.get(0).getBookingCode()).isEqualTo("testCode001");
        Assertions.assertThat(resp.get(1).getScheduleCode()).isEqualTo("schedule002");
    }

    @Test
    void getReservationByBookingCode() {
        String bookingCode = "testCode001";
        Reservation expected = Reservation.builder()
                .bookingCode("testCode001")
                .createdAt(LocalDateTime.now())
                .nik("12345678")
                .scheduleCode("schedule001")
                .status("B")
                .createdBy("username").build();

        when(reservationClientService.getReservationByBookingCode(any(String.class))).thenReturn(expected);

        Reservation resp =
                reservationService.getReservationByBookingCode(bookingCode);

        Assertions.assertThat(resp.getBookingCode()).isEqualTo("testCode001");
    }
}