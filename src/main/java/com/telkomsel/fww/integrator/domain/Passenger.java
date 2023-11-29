package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Passenger {

    private String nik;

    private String fullName;

    private String title;

    private LocalDateTime createdAt;

}
