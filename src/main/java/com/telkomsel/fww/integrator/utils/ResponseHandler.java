package com.telkomsel.fww.integrator.utils;

import com.telkomsel.fww.integrator.common.ResponseCode;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(ResponseCode responseCode,
                                                          HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("status_desc", responseCode.getDescription());
        map.put("status_code", responseCode.getCode());
        if (!status.equals(HttpStatus.NO_CONTENT)) {
            map.put("data", responseObj);
        }

        return new ResponseEntity<>(map, status);
    }
}
