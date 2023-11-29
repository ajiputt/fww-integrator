package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {

    private String code;

    private LocalDate date;

    private LocalTime timeDeparture;

    private Integer duration;

    private Integer baggageWeight;

    private BigDecimal price;

    private Plane plane;

    private Airport airportDeparture;

    private Airport airportArrival;

    private List<ScheduleSeats> availableSeats;
}
