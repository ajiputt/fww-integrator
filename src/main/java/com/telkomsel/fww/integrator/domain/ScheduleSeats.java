package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleSeats {

    private Integer id;

    private Integer seatNo;

    private String status;
}
