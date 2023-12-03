package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Passenger;
import com.telkomsel.fww.integrator.feign.service.PassengerClientService;
import com.telkomsel.fww.integrator.payload.request.RequestPassenger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PassengerService {

    private final HttpExternalService httpExternalService;

    private final PassengerClientService passengerClientService;

    public PassengerService(HttpExternalService httpExternalService,
                            PassengerClientService passengerClientService) {
        this.httpExternalService = httpExternalService;
        this.passengerClientService = passengerClientService;
    }

    public Passenger postPassenger(RequestPassenger requestPassenger) {

        httpExternalService.getDisdukcapil(requestPassenger.getNik());

        httpExternalService.getPeduliLindungi(requestPassenger.getNik());

        return passengerClientService.postPassenger(Passenger.builder()
                .fullName(requestPassenger.getFullName())
                .nik(requestPassenger.getNik())
                .title(requestPassenger.getTitle())
                .createdAt(LocalDateTime.now()).build());
    }
}
