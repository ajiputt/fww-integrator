package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.feign.service.ScheduleClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleClientService scheduleClientService;


    public ScheduleService(ScheduleClientService scheduleClientService) {
        this.scheduleClientService = scheduleClientService;
    }

    public List<Schedule> getSchedule(String departure, String arrival,
                                      String date) {
        return scheduleClientService.getSchedules(departure, arrival, date).getEmbedded().getSchedules();
    }
}
