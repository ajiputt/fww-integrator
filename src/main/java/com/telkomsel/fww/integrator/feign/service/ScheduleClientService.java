package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.Schedule;
import com.telkomsel.fww.integrator.feign.response.ScheduleEmbeddedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "schedules", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface ScheduleClientService {

    @GetMapping("/schedules/search/findByAirportDepartureCodeAndAirportArrivalCodeAndDate?projection=schedule-view")
    ScheduleEmbeddedResponse getSchedules(@RequestParam("departure") String departure,
                                          @RequestParam("arrival") String arrival,
                                          @RequestParam("date") String date);

    @GetMapping("/schedules/search/findByCode?projection=schedule-view")
    Schedule getSchedulesByCode(@RequestParam("code") String code);

}
