package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpiredReservationJobServiceTest {

    ExpiredReservationJobService expiredReservationJobService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private JobExecutionContext context;

    @BeforeEach
    void init() {
        expiredReservationJobService = new ExpiredReservationJobService(reservationService);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("reservationCode", "test");
        when(context.getMergedJobDataMap()).thenReturn(jobDataMap);
    }

    @Test
    void executeInternal() throws SchedulerException {
        when(reservationService.updateReservation(any(), any())).thenReturn(Reservation.builder().build());
        expiredReservationJobService.executeInternal(context);

        verify(reservationService).updateReservation("test", "expired");
    }
}