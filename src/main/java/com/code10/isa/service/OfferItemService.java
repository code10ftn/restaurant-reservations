package com.code10.isa.service;

import com.code10.isa.model.OfferItem;
import com.code10.isa.repository.OfferItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferItemService {

    private final OfferItemRepository offerItemRepository;

    @Autowired
    public OfferItemService(OfferItemRepository offerItemRepository) {
        this.offerItemRepository = offerItemRepository;
    }

    public OfferItem create(OfferItem offerItem) {
        return offerItemRepository.save(offerItem);
    }
}
