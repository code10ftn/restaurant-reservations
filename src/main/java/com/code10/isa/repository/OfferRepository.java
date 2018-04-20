package com.code10.isa.repository;


import com.code10.isa.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByRestaurantId(long id);

    Optional<Offer> findById(long id);
}
