package com.telkomsel.fww.integrator.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telkomsel.fww.integrator.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReservationEmbeddedResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    @Data
    public static class Embedded {
        @JsonProperty("reservations")
        private List<Reservation> reservations = new ArrayList<>();

    }
}
