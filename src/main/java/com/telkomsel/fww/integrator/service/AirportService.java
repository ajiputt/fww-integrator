package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Airport;
import com.telkomsel.fww.integrator.feign.service.AirportClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportClientService airportClientService;


    public AirportService(AirportClientService airportClientService) {
        this.airportClientService = airportClientService;
    }

    public List<Airport> getAirport() {
        return airportClientService.getAirports().getEmbedded().getAirports();
    }
}
