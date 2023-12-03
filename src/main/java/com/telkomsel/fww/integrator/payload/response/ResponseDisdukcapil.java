package com.telkomsel.fww.integrator.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDisdukcapil {

    private String nik;

    private String name;

    private String address;

    private String city;

    private String province;

    private String expired;

    private String bod;

    private String blood_type;
}
