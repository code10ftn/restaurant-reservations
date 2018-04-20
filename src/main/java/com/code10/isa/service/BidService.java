package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Bid;
import com.code10.isa.model.BidStatus;
import com.code10.isa.model.Offer;
import com.code10.isa.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepository;

    @Autowired
    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public Bid create(Bid bid, Offer offer) {
        bid.setOffer(offer);
        return bidRepository.save(bid);
    }

    public List<Bid> findByOffer(long offerId) {
        return bidRepository.findByOfferId(offerId);
    }


    public Bid findByOfferAndSupplier(long offerId, long supplierId) {
        return bidRepository.findByOfferIdAndSupplierId(offerId, supplierId);
    }

    public Bid findById(long id) {
        return bidRepository.findById(id).orElseThrow(() -> new NotFoundException("Bid not found."));
    }

//    public Bid accept(long id) {
//        Bid bid = findById(id);
//        bid.setStatus(BidStatus.ACCEPTED);
//        Bid acceptedBid = bidRepository.save(bid);
//
//        List<Bid> bids = bidRepository.findByOfferId(bid.getOffer().getId());
//        for (Bid currBid : bids) {
//            if (currBid.getId() != bid.getId()) {
//                currBid.setStatus(BidStatus.DECLINED);
//                bidRepository.save(currBid);
//            }
//        }
//
//        return acceptedBid;
//
//    }

    public Bid update(Bid updatedBid) {
        Bid bid = findById(updatedBid.getId());
        bid.update(updatedBid);
        return bidRepository.save(bid);
    }

    public List<Bid> findBySupplier(long id) {
        return bidRepository.findBySupplierId(id);
    }

    public Bid acceptBid(long id) {
        Bid bid = findById(id);
        bid.setStatus(BidStatus.ACCEPTED);
        return bidRepository.save(bid);
    }
}
