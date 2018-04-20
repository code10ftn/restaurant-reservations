package com.code10.isa.repository;

import com.code10.isa.model.ReservationGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationGuestRepository extends JpaRepository<ReservationGuest, Long> {

    Optional<ReservationGuest> findByInvitationCode(String invitationCode);

    Optional<ReservationGuest> findById(long id);

    List<ReservationGuest> findByGuestId(long userId);

    List<ReservationGuest> findByReservationId(long reservationId);

    Optional<ReservationGuest> findByReservationIdAndGuestId(long reservationId, long id);
}
