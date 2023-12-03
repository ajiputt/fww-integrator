package com.telkomsel.fww.integrator.job;

import com.telkomsel.fww.integrator.service.ExpiredReservationJobService;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class QuartzJob {

    public JobDetail buildJobDetail(String reservationCode) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("reservationCode", reservationCode);

        return JobBuilder.newJob(ExpiredReservationJobService.class)
                .withIdentity(reservationCode, "expired-jobs")
                .withDescription("Expired Job for reservation code : " + reservationCode)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "expired-triggers")
                .withDescription("Execute Expired Job Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
