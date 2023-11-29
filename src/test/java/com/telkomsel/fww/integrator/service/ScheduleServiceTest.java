package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.feign.response.ScheduleEmbeddedResponse;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    ScheduleService scheduleService;

    @Mock
    private ScheduleClientService scheduleClientService;

    @BeforeEach
    void init() {
        scheduleService = new ScheduleService(scheduleClientService);
    }

    @Test
    void getSchedule() {
        String departure = "BDO";
        String arrival = "CGK";
        String date = "25122023";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        List<Schedule> scheduleList =
                Arrays.asList(Schedule.builder()
                                .code("schedule001")
                                .airportArrival(Airport.builder().code("CGK").build())
                                .airportDeparture(Airport.builder().code("BDO").build())
                                .date(LocalDate.parse("25122023", formatter))
                                .price(new BigDecimal(10000)).build(),
                        Schedule.builder()
                                .code("schedule002")
                                .airportArrival(Airport.builder().code("CGK").build())
                                .airportDeparture(Airport.builder().code("BDO").build())
                                .date(LocalDate.parse("25122023", formatter))
                                .price(new BigDecimal(20000)).build());
        ScheduleEmbeddedResponse.Embedded embedded =
                new ScheduleEmbeddedResponse.Embedded();
        embedded.setSchedules(scheduleList);
        ScheduleEmbeddedResponse expected =
                ScheduleEmbeddedResponse.builder()
                        .embedded(embedded)
                        .build();
        when(scheduleClientService.getSchedules(any(), any(), any())).thenReturn(expected);

        List<Schedule> resp = scheduleService.getSchedule(departure, arrival,
                date);

        Assertions.assertThat(resp).hasSize(2);
        Assertions.assertThat(resp.get(0).getCode()).isEqualTo("schedule001");
        Assertions.assertThat(resp.get(1).getPrice()).isEqualTo(new BigDecimal(20000));

    }
}