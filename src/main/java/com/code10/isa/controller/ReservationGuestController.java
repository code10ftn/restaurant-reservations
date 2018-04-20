package com.code10.isa.controller;

import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.user.Guest;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.OrderService;
import com.code10.isa.service.ReservationGuestService;
import com.code10.isa.service.ReservationService;
import com.code10.isa.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/reservationGuests")
public class ReservationGuestController {

    private final ReservationGuestService reservationGuestService;

    private final ReservationService reservationService;

    private final OrderService orderService;

    private final AuthService authService;

    @Autowired
    public ReservationGuestController(ReservationGuestService reservationGuestService, ReservationService reservationService, OrderService orderService, AuthService authService) {
        this.reservationGuestService = reservationGuestService;
        this.reservationService = reservationService;
        this.orderService = orderService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable long id) {
        final ReservationGuest reservationGuest = reservationGuestService.findById(id);

        final Guest host = (Guest) authService.getCurrentUser();
        if (reservationGuest.getGuest().getId() != host.getId()) {
            throw new ForbiddenException();
        }

        final boolean cancellable = DateUtil.getDifferenceInMinutes(reservationGuest.getReservation().getDate(), new Date()) > 30;
        if (!cancellable) {
            throw new ForbiddenException("Reservation cannot be canceled less than 30 minutes before it starts!");
        }

        if (reservationGuest.isHost()) {
            //orderService.remove(reservationGuest.getReservation().getOrder());
            reservationGuestService.removeReservationGuests(reservationGuest.getReservation());
            //reservationService.remove(reservationGuest.getReservation());
        } else {
            reservationGuestService.remove(reservationGuest);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
