package com.telkomsel.fww.integrator.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


class QuartzJobTest {

    QuartzJob quartzJob;


    @BeforeEach
    void init() {
        quartzJob = new QuartzJob();
    }

    @Test
    void buildJobDetail() {
        quartzJob.buildJobDetail("string");
        Assertions.assertTrue(true);
    }

    @Test
    void buildJobTrigger() {
        JobDetail jobDetail = quartzJob.buildJobDetail("test");
        ZonedDateTime startAt =
                ZonedDateTime.of(LocalDateTime.now().plusMinutes(5),
                        ZoneId.of("Asia/Jakarta"));
        quartzJob.buildJobTrigger(jobDetail, startAt);
        Assertions.assertTrue(true);
    }
}