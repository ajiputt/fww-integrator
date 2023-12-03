package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.aspect.LogController;
import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.domain.Baggage;
import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.payload.request.RequestCheckin;
import com.telkomsel.fww.integrator.payload.request.RequestReservation;
import com.telkomsel.fww.integrator.service.BaggageService;
import com.telkomsel.fww.integrator.service.ReservationService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    private final BaggageService baggageService;

    public ReservationController(ReservationService reservationService, BaggageService baggageService) {
        this.reservationService = reservationService;
        this.baggageService = baggageService;
    }

    @LogController
    @PostMapping("/v1/reservations")
    public ResponseEntity<Object> postReservation(@Valid @RequestBody RequestReservation requestReservation,
                                                  Principal principal) throws SchedulerException {
        Reservation resp =
                reservationService.postReservation(requestReservation,
                        principal.getName());
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

    @LogController
    @PutMapping(value = "/v1/reservations/cancel/{booking-code}")
    public ResponseEntity<Object> cancelReservation(@PathVariable(
            "booking-code") String bookingCode) throws SchedulerException {
        Reservation resp =
                reservationService.updateReservation(bookingCode, "cancel");
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }

    @LogController
    @PutMapping(value = "/v1/reservations/callback/success/{booking-code}")
    public ResponseEntity<Object> callbackSuccessReservation(@PathVariable(
            "booking-code") String bookingCode) throws SchedulerException {
        Reservation resp =
                reservationService.updateReservation(bookingCode, "success");
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }

    @LogController
    @PostMapping(value = "/v1/reservations/checkin")
    public ResponseEntity<Object> updateCheckInReservation(@Valid @RequestBody RequestCheckin requestCheckin) {
        Baggage resp =
                baggageService.postBaggage(requestCheckin);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }


}
