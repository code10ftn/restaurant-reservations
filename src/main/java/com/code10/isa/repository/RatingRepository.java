package com.code10.isa.repository;

import com.code10.isa.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findById(long id);

    Rating findByReservationGuestId(long id);

    List<Rating> findByReservationGuestReservationRestaurantId(long id);

    List<Rating> findByReservationGuestReservationOrderWaiterId(long id);

    List<Rating> findByReservationGuestGuestId(long id);

    @Query(value = "select r from Rating r join r.reservationGuest resg join resg.reservation res join res.order ord join ord.orderItems oi where oi.menuItem.id = :id")
    List<Rating> findByMenuItem(@Param("id") long id);
}
