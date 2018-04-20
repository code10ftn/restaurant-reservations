package com.code10.isa.repository;

import com.code10.isa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRestaurantId(long id);

    Optional<Reservation> findById(long id);

    @Query(value = "select r from Reservation r join r.tables t where t.id = :id")
    List<Reservation> findByTable(@Param("id") long id);
}
