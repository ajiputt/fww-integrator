package com.telkomsel.fww.integrator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Airport {

    private String code;

    private String name;

    private String city;
}
