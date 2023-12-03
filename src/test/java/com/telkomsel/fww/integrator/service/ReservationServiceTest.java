package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.exception.PaymentException;
import com.telkomsel.fww.integrator.feign.response.ReservationEmbeddedResponse;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import com.telkomsel.fww.integrator.job.QuartzJob;
import com.telkomsel.fww.integrator.payload.request.RequestReservation;
import com.telkomsel.fww.integrator.payload.response.ResponseMidtrans;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    ReservationService reservationService;

    @Mock
    private ReservationClientService reservationClientService;

    @Mock
    private QueueSenderService queueSenderService;

    @Mock
    private QuartzJob quartzJob;

    @Mock
    private Scheduler scheduler;

    @Mock
    private HttpExternalService httpExternalService;

    @BeforeEach
    void init() {
        reservationService = new ReservationService(reservationClientService,
                queueSenderService, quartzJob, scheduler, httpExternalService);
    }

    @Test
    void postReservation() throws SchedulerException {
        ReflectionTestUtils.setField(reservationService, "zoneId", "UTC");
        String bookingCode = "BOOK-FWW-" + RandomStringUtils.random(5,
                true, true);

        RequestReservation request = RequestReservation.builder()
                .nik("12345678")
                .scheduleCode("schedule001")
                .seatNo(1).build();

        ResponseMidtrans responseMidtrans =
                ResponseMidtrans.builder().statusCode("00").build();

        when(httpExternalService.sendPaymentInfo(any(), any())).thenReturn(responseMidtrans);


        when(reservationClientService.postReservation(any(Reservation.class)))
                .thenReturn(Reservation.builder()
                        .bookingCode(bookingCode)
                        .nik(request.getNik())
                        .scheduleCode(request.getScheduleCode())
                        .seatNo(request.getSeatNo()).build());
        Reservation resp =
                reservationService.postReservation(request, "username");

        assertEquals(bookingCode, resp.getBookingCode());
    }

    @Test
    void postReservationFailed() {
        ResponseMidtrans responseMidtrans =
                ResponseMidtrans.builder().statusCode("01").build();

        when(httpExternalService.sendPaymentInfo(any(), any())).thenReturn(responseMidtrans);


        assertThrows(PaymentException.class,
                () -> reservationService.postReservation(RequestReservation.builder().build(), "username"));
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