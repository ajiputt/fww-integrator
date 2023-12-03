package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class EmailService {

    private final ReservationClientService reservationClientService;

    private final ScheduleClientService scheduleClientService;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(ReservationClientService reservationClientService,
                        ScheduleClientService scheduleClientService, JavaMailSender javaMailSender,
                        TemplateEngine templateEngine) {
        this.reservationClientService = reservationClientService;
        this.scheduleClientService = scheduleClientService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String bookingCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        Reservation reservation =
                reservationClientService.getReservationByBookingCode(bookingCode);

        Schedule schedule =
                scheduleClientService.getSchedulesByCode(reservation.getSchedule().getCode());

        helper.setFrom("ajiperdanaputra90@gmail.com");
        helper.setTo("ajiperdanaputra90@gmail.com");
        helper.setSubject("Flight Has Been Booking");
        String htmlContent = templateEngine.process("email-confirmation",
                new Context());
        htmlContent = htmlContent.replaceAll("##BOOKING_CODE##",
                reservation.getBookingCode()).replaceAll("##DEPARTURE##",
                schedule.getAirportDeparture().getName()).replaceAll("##DESTINATION##",
                schedule.getAirportArrival().getName()).replaceAll("##PAYMENT_METHOD##",
                "VA MADTRINS").replaceAll("##PRICE##",
                reservation.getSchedule().getPrice().toPlainString());
        helper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
    }
}
