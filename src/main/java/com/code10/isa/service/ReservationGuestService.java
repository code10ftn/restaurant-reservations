package com.code10.isa.service;

import com.code10.isa.controller.exception.AuthorizationException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Reservation;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.user.Guest;
import com.code10.isa.repository.ReservationGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationGuestService {

    private final ReservationGuestRepository reservationGuestRepository;

    @Autowired
    public ReservationGuestService(ReservationGuestRepository reservationGuestRepository) {
        this.reservationGuestRepository = reservationGuestRepository;
    }

    public ReservationGuest create(Reservation reservation, Guest guest, boolean isHost) {
        final ReservationGuest reservationGuest = new ReservationGuest(reservation, guest, isHost, false);
        if (isHost) {
            reservationGuest.setAccepted(true);
        }
        return reservationGuestRepository.save(reservationGuest);
    }

    public ReservationGuest acceptInvitation(String invitationCode) {
        final ReservationGuest guest = reservationGuestRepository
                .findByInvitationCode(invitationCode).orElseThrow(() -> new AuthorizationException("Invalid invitation code!"));
        guest.setAccepted(true);
        return reservationGuestRepository.save(guest);
    }

    public ReservationGuest findById(long id) {
        return reservationGuestRepository.findById(id).orElseThrow(() -> new AuthorizationException("ReservationGuest not found!"));
    }

    public List<ReservationGuest> findByReservation(long id) {
        return reservationGuestRepository.findByReservationId(id);
    }

    public ReservationGuest findByReservationAndGuest(long reservationId, Guest guest) {
        return reservationGuestRepository.findByReservationIdAndGuestId(reservationId, guest.getId()).orElseThrow(NotFoundException::new);
    }

    public void remove(ReservationGuest reservationGuest) {
        reservationGuestRepository.delete(reservationGuest);
    }

    public void removeReservationGuests(Reservation reservation) {
        final List<ReservationGuest> reservationGuests = reservationGuestRepository.findByReservationId(reservation.getId());
        reservationGuests.forEach(reservationGuestRepository::delete);
    }
}
