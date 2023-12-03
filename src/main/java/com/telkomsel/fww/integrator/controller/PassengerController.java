package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.aspect.LogController;
import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.domain.Passenger;
import com.telkomsel.fww.integrator.payload.request.RequestPassenger;
import com.telkomsel.fww.integrator.service.PassengerService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @LogController
    @PostMapping("/v1/passengers")
    public ResponseEntity<Object> postPassenger(@RequestBody RequestPassenger requestPassenger) {
        Passenger resp = passengerService.postPassenger(requestPassenger);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS_CREATE,
                HttpStatus.CREATED, resp);
    }
}
