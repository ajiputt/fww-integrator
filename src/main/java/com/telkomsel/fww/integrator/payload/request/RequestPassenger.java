package com.telkomsel.fww.integrator.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPassenger {

    @NotEmpty
    private String nik;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String title;
}
