package com.telkomsel.fww.integrator.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomsel.fww.integrator.domain.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportEmbeddedResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    @Data
    public static class Embedded {
        @JsonProperty("airports")
        private List<Airport> airports = new ArrayList<>();

    }
}
