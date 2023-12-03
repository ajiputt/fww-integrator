package com.telkomsel.fww.integrator.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMidtrans {

    private String statusCode;

    private String statusDesc;
}
