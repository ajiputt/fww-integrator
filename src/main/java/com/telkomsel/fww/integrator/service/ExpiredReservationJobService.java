package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExpiredReservationJobService extends QuartzJobBean {

    private final ReservationService reservationService;

    public ExpiredReservationJobService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {

        JobDataMap jobDataMap = context.getMergedJobDataMap();

        try {
            Reservation reservation = reservationService.updateReservation(jobDataMap.getString(
                    "reservationCode"), "expired");
            log.info("Schedule Running with Reservation Code : {}", reservation.getBookingCode());
        } catch (SchedulerException e) {
            log.error("Error Scheduler : {}", e.getMessage());
            throw new RuntimeException(e);
        }


    }
}
