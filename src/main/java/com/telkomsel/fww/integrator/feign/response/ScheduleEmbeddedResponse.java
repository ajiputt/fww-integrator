package com.telkomsel.fww.integrator.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomsel.fww.integrator.domain.Schedule;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ScheduleEmbeddedResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    @Data
    public static class Embedded {
        @JsonProperty("schedules")
        private List<Schedule> schedules = new ArrayList<>();

    }
}
