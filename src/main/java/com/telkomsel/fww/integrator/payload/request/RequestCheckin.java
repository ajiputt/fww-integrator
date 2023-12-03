package com.telkomsel.fww.integrator.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RequestCheckin {

    @NotEmpty
    private String bookingCode;

    @NotNull
    private BigDecimal weight;

    private String description;
}
