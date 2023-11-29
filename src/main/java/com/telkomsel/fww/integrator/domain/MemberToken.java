package com.telkomsel.fww.integrator.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberToken {

    private String username;

    private String token;
}
