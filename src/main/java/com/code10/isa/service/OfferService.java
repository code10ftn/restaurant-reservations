package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Offer;
import com.code10.isa.model.OfferItem;
import com.code10.isa.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }

    public List<Offer> findByRestaurant(long restaurantId) {
        return offerRepository.findByRestaurantId(restaurantId);
    }

    public Offer findById(long id) {
        return offerRepository.findById(id).orElseThrow(() -> new NotFoundException("Offer not found!"));
    }

    public Offer update(long id, Offer updatedOffer) {

        Offer offer = findById(id);
        offer.update(updatedOffer);
        offer.getOfferItems().clear();
        for (OfferItem offerItem : updatedOffer.getOfferItems()) {
            offerItem.setOffer(offer);
            offer.getOfferItems().add(offerItem);
        }
        return offerRepository.save(offer);
    }
}
