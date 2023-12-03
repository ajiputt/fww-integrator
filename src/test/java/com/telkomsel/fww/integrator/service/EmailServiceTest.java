package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    EmailService emailService;

    @Mock
    private ReservationClientService reservationClientService;

    @Mock
    private ScheduleClientService scheduleClientService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @BeforeEach
    void init() {
        emailService = new EmailService(reservationClientService,
                scheduleClientService, javaMailSender, templateEngine);
    }

    @Test
    void sendEmail() throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        Reservation reservation = Reservation.builder()
                .schedule(Schedule.builder().code("code")
                        .price(new BigDecimal(2000)).build())
                .bookingCode("testBookingCode")
                .build();

        Schedule schedule = Schedule.builder()
                .airportDeparture(Airport.builder().name("Departure").build())
                .airportArrival(Airport.builder().name("Arrival").build())
                .price(new BigDecimal(2000)).build();
        when(reservationClientService.getReservationByBookingCode(anyString())).thenReturn(reservation);

        when(scheduleClientService.getSchedulesByCode(anyString())).thenReturn(schedule);

        when(templateEngine.process(anyString(), any())).thenReturn("email body");

        emailService.sendEmail("Test");

        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}