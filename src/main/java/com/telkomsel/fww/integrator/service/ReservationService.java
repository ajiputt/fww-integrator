package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.feign.service.ReservationClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationClientService reservationClientService;


    public ReservationService(ReservationClientService reservationClientService) {
        this.reservationClientService = reservationClientService;
    }

    public Reservation postReservation(Reservation reservation) {
        return reservationClientService.postReservation(reservation);
    }

    public List<Reservation> getReservationByUser(String username) {
        return reservationClientService.getReservationByUser(username).getEmbedded().getReservations();
    }

    public Reservation getReservationByBookingCode(String bookingCode) {
        return reservationClientService.getReservationByBookingCode(bookingCode);
    }
}
