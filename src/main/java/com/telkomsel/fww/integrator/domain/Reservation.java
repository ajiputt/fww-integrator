package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reservation {

    private Integer id;

    private String bookingCode;

    private String createdBy;

    private LocalDateTime createdAt;

    private String status;

    private Integer seatNo;

    private String scheduleCode;

    @NotEmpty(message = "NIK is mandatory")
    private String nik;

    private Schedule schedule;

    private Passenger passenger;
}
