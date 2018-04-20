package com.code10.isa.controller;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Order;
import com.code10.isa.model.Reservation;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.dto.ReservationDto;
import com.code10.isa.model.user.Guest;
import com.code10.isa.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    private final ReservationGuestService reservationGuestService;

    private final OrderService orderService;

    private final AuthService authService;

    private final MailService mailService;

    private final HttpServletRequest request;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationGuestService reservationGuestService,
                                 OrderService orderService, AuthService authService, MailService mailService, HttpServletRequest request) {
        this.reservationService = reservationService;
        this.reservationGuestService = reservationGuestService;
        this.orderService = orderService;
        this.authService = authService;
        this.mailService = mailService;
        this.request = request;
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody ReservationDto reservationDto) {
        final Order order = orderService.create(reservationDto.getOrder());
        final Reservation reservation = reservationService.create(reservationDto.getReservation(), order);
        orderService.setReservation(order, reservation);

        final Guest host = (Guest) authService.getCurrentUser();
        final ReservationGuest reservationHost = reservationGuestService.create(reservation, host, true);

        for (Guest guest : reservationDto.getGuests()) {
            ReservationGuest reservationGuest = reservationGuestService.create(reservation, guest, false);
            mailService.sendReservationMail(request.getRequestURL().toString(), host, reservationGuest, guest.getEmail());
        }

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @GetMapping("/accept/{invitationCode}")
    public ModelAndView acceptInvitation(@PathVariable String invitationCode) {
        reservationGuestService.acceptInvitation(invitationCode);
        return new ModelAndView(new RedirectView("/", true));
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Reservation reservation = reservationService.findById(id);
        final Guest currentGuest = (Guest) authService.getCurrentUser();

        final List<ReservationGuest> reservationGuests = reservationGuestService.findByReservation(reservation.getId());
        reservation.setReservationGuests(reservationGuests);

        for (ReservationGuest reservationGuest : reservationGuests) {
            if (reservationGuest.getGuest().getId() == currentGuest.getId()) {
                return new ResponseEntity<>(reservation, HttpStatus.OK);
            }
        }

        throw new NotFoundException("Reservation not found!");
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/user/{userId}")
    public ResponseEntity findUserReservations(@PathVariable long userId) {
        final List<Reservation> userReservations = reservationService.findUserReservations(userId);
        return new ResponseEntity<>(userReservations, HttpStatus.OK);
    }
}
