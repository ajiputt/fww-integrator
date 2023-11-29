package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.aspect.LogController;
import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.service.AirportService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @LogController
    @GetMapping("/v1/airports")
    public ResponseEntity<Object> getAirports() {
        List<Airport> resp = airportService.getAirport();
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }
}
