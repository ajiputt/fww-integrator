package com.telkomsel.fww.integrator.feign.service;

import com.telkomsel.fww.integrator.config.FeignClientConfig;
import com.telkomsel.fww.integrator.domain.Reservation;
import com.telkomsel.fww.integrator.feign.response.ReservationEmbeddedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "reservations", url = "${fww.core.url}", configuration =
        FeignClientConfig.class)
public interface ReservationClientService {

    @PostMapping("/reservations")
    Reservation postReservation(@RequestBody Reservation reservation);

    @GetMapping("/reservations/search/findByCreatedBy?projection=reservation-view")
    ReservationEmbeddedResponse getReservationByUser(@RequestParam("username") String username);

    @GetMapping("/reservations/search/findByBookingCode?projection=reservation-view")
    Reservation getReservationByBookingCode(@RequestParam("booking") String bookingCode);

}
