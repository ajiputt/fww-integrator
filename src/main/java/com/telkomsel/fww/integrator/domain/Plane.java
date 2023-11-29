package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plane {
    private String code;

    private String name;

    private Integer noOfSeats;

}
