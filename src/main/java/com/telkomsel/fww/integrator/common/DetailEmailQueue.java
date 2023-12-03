package com.telkomsel.fww.integrator.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailEmailQueue {

    private String bookingCode;

    private String action;
}
