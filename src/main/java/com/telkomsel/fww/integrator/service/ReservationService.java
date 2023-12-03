package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.exception.PaymentException;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import com.telkomsel.fww.integrator.job.QuartzJob;
import com.telkomsel.fww.integrator.payload.request.RequestReservation;
import com.telkomsel.fww.integrator.payload.response.ResponseMidtrans;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationClientService reservationClientService;

    private final QueueSenderService queueSenderService;

    private final QuartzJob quartzJob;

    private final Scheduler scheduler;


    private final HttpExternalService httpExternalService;


    public ReservationService(ReservationClientService reservationClientService, QueueSenderService queueSenderService, QuartzJob quartzJob, Scheduler scheduler, HttpExternalService httpExternalService) {
        this.reservationClientService = reservationClientService;
        this.queueSenderService = queueSenderService;
        this.quartzJob = quartzJob;
        this.scheduler = scheduler;
        this.httpExternalService = httpExternalService;
    }

    public Reservation postReservation(RequestReservation requestReservation,
                                       String username) throws SchedulerException {

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[5];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);

        String bookingCode = "BOOK-FWW-" + token;

        ResponseMidtrans responseMidtrans =
                httpExternalService.sendPaymentInfo(bookingCode,
                        requestReservation.getScheduleCode());

        if (responseMidtrans.getStatusCode().equals("00")) {

            Reservation response =
                    reservationClientService.postReservation(Reservation.builder()
                            .bookingCode(bookingCode)
                            .status("B")
                            .nik(requestReservation.getNik())
                            .seatNo(requestReservation.getSeatNo())
                            .scheduleCode(requestReservation.getScheduleCode())
                            .createdBy(username)
                            .createdAt(LocalDateTime.now()).build());

            ZonedDateTime expiredTime =
                    ZonedDateTime.of(LocalDateTime.now().plusMinutes(5),
                            ZoneId.of("Asia/Jakarta"));

            JobDetail jobDetail = quartzJob.buildJobDetail(response.getBookingCode());
            Trigger trigger = quartzJob.buildJobTrigger(jobDetail, expiredTime);
            scheduler.scheduleJob(jobDetail, trigger);

            queueSenderService.sendQueue(response.getBookingCode());
            return response;
        } else {
            throw new PaymentException();
        }

    }

    public Reservation updateReservation(String bookingCode, String action) throws SchedulerException {
        Reservation reservation =
                reservationClientService.getReservationByBookingCode(bookingCode);

        switch (action) {
            case "success" -> {
                reservation.setStatus("P");
                scheduler.unscheduleJob(TriggerKey.triggerKey(bookingCode, "expired-triggers"));
            }
            case "cancel" -> {
                reservation.setStatus("C");
                scheduler.unscheduleJob(TriggerKey.triggerKey(bookingCode, "expired-triggers"));
            }
            case "checkin" -> reservation.setStatus("D");
            default -> reservation.setStatus("E");
        }
        return reservationClientService.updateReservation(reservation);
    }

    public List<Reservation> getReservationByUser(String username) {
        return reservationClientService.getReservationByUser(username).getEmbedded().getReservations();
    }

    public Reservation getReservationByBookingCode(String bookingCode) {
        return reservationClientService.getReservationByBookingCode(bookingCode);
    }
}
