package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.aspect.LogController;
import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.service.ReservationService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @LogController
    @PostMapping("/v1/reservations")
    public ResponseEntity<Object> postReservation(@Valid @RequestBody Reservation reservation) {
        Reservation resp = reservationService.postReservation(reservation);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS_CREATE,
                HttpStatus.CREATED, resp);
    }

    @LogController
    @GetMapping(value = "/v1/reservations", params = "booking")
    public ResponseEntity<Object> getReservations(@RequestParam(
            "booking") String bookingCode) {
        Reservation resp =
                reservationService.getReservationByBookingCode(bookingCode);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }

    @LogController
    @GetMapping(value = "/v1/reservations", params = "username")
    public ResponseEntity<Object> getReservationsByUser(@RequestParam(
            "username") String username) {
        List<Reservation> resp = reservationService.getReservationByUser(username);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }
}
