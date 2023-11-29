package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.aspect.LogController;
import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.service.ScheduleService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @LogController
    @GetMapping("/v1/schedules")
    public ResponseEntity<Object> getSchedules(@RequestParam("departure") String departure,
                                               @RequestParam("arrival") String arrival,
                                               @RequestParam("date") String date) {
        List<Schedule> resp = scheduleService.getSchedule(departure, arrival, date);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }
}
