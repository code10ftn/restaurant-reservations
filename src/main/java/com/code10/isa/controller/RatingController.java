package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Rating;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.user.Guest;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.RatingService;
import com.code10.isa.service.ReservationGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private final ReservationGuestService reservationGuestService;
    private final AuthService authService;

    @Autowired
    public RatingController(RatingService ratingService, ReservationGuestService reservationGuestService, AuthService authService) {
        this.ratingService = ratingService;
        this.reservationGuestService = reservationGuestService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/reservation/{id}/restaurant")
    public ResponseEntity<Rating> rateRestaurant(@PathVariable long id, @RequestParam int rate) {
        final ReservationGuest reservationGuest = reservationGuestService.findByReservationAndGuest(id, (Guest) authService.getCurrentUser());
        final Rating rating = ratingService.rateRestaurant(reservationGuest, rate);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/reservation/{id}/waiter")
    public ResponseEntity<Rating> rateWaiter(@PathVariable long id, @RequestParam int rate) {
        final ReservationGuest reservationGuest = reservationGuestService.findByReservationAndGuest(id, (Guest) authService.getCurrentUser());
        if (reservationGuest.getReservation().getOrder().getOrderItems().size() == 0) {
            throw new BadRequestException("Reservation has no waiter!");
        }

        final Rating rating = ratingService.rateWaiter(reservationGuest, rate);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/reservation/{id}/order")
    public ResponseEntity<Rating> rateOrder(@PathVariable long id, @RequestParam int rate) {
        final ReservationGuest reservationGuest = reservationGuestService.findByReservationAndGuest(id, (Guest) authService.getCurrentUser());
        if (reservationGuest.getReservation().getOrder().getOrderItems().size() == 0) {
            throw new BadRequestException("Reservation has no order!");
        }

        final Rating rating = ratingService.rateOrder(reservationGuest, rate);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
}
