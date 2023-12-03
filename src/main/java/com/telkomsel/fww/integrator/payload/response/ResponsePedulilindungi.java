package com.telkomsel.fww.integrator.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePedulilindungi {

    private String nik;

    private String name;

    private String address;

    private List<Vaccine> vaccine;

    @Data
    @Builder
    public static class Vaccine {

        private String type;

        private String url;

        private Integer order;

    }
}
