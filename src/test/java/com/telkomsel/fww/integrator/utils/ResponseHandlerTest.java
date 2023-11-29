package com.telkomsel.fww.integrator.utils;

import com.telkomsel.fww.integrator.common.ResponseCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ResponseHandlerTest {


    @Test
    void generateResponse() {

        ResponseCode responseCode = ResponseCode.SUCCESS;
        HttpStatus status = HttpStatus.OK;
        String obj = "test";

        ResponseEntity<Object> resp =
                ResponseHandler.generateResponse(responseCode, status, obj);

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}