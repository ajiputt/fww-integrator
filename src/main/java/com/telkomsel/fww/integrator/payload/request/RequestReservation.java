package com.telkomsel.fww.integrator.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestReservation {

    @NotEmpty
    private String nik;

    @NotNull
    private Integer seatNo;

    @NotEmpty
    private String scheduleCode;
}
