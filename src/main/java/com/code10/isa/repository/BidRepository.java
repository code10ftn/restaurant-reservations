package com.code10.isa.repository;


import com.code10.isa.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByOfferId(long id);

    Bid findByOfferIdAndSupplierId(long offerId, long supplierId);

    Optional<Bid> findById(long id);

    List<Bid> findBySupplierId(long id);
}
