package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Bid;
import com.code10.isa.model.BidStatus;
import com.code10.isa.model.Notification;
import com.code10.isa.model.Offer;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Supplier;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.BidService;
import com.code10.isa.service.NotificationService;
import com.code10.isa.service.OfferService;
import com.code10.isa.util.NotificationConstants;
import com.code10.isa.util.TransactionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    private final BidService bidService;

    private final AuthService authService;

    private final OfferService offerService;

    private final NotificationService notificationService;

    @Autowired
    public BidController(BidService bidService, AuthService authService, OfferService offerService, NotificationService notificationService) {
        this.bidService = bidService;
        this.authService = authService;
        this.offerService = offerService;
        this.notificationService = notificationService;
    }


    @PreAuthorize("hasAuthority('SUPPLIER')")
    @PostMapping
    public ResponseEntity create(@RequestBody Bid bid) {
        Supplier supplier = (Supplier) authService.getCurrentUser();
        bid.setSupplier(supplier);
        final Offer offer = offerService.findById(bid.getOffer().getId());
        Date now = new Date();
        if (offer.getStartDate().after(now) || offer.getEndDate().before(now)) {
            throw new BadRequestException("Offer expired!");
        }
        if (bidService.findByOfferAndSupplier(offer.getId(), supplier.getId()) != null) {
            throw new ForbiddenException("Bid for this offer already exists.");
        }
        if (offer.getRestaurant().getId() != supplier.getRestaurant().getId()) {
            throw new ForbiddenException("Cannot make bid for this offer.");
        }

        Bid createdBid = bidService.create(bid, offer);
        return new ResponseEntity<>(createdBid, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SUPPLIER')")
    @GetMapping("/offer/{id}/me")
    public ResponseEntity getMyOffer(@PathVariable long id) {

        Supplier supplier = (Supplier) authService.getCurrentUser();

        Bid bid = bidService.findByOfferAndSupplier(id, supplier.getId());

        if (bid == null) {
            throw new NotFoundException("No bid found for this offer.");
        }

        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}/accept")
    public ResponseEntity acceptBid(@PathVariable long id) {
        Manager manager = (Manager) authService.getCurrentUser();

        Bid bid = bidService.findById(id);
        if (manager.getRestaurant().getId() != bid.getSupplier().getRestaurant().getId()) {
            throw new ForbiddenException("Not a manager of this restaurant!");
        }
        if (bid.getStatus() != BidStatus.ACTIVE) {
            throw new BadRequestException("Bid not active!");
        }

        bid = bidService.acceptBid(bid.getId());
        notificationService.saveAndSendNotification(new Notification(String.valueOf(bid.getSupplier().getId()), NotificationConstants.BID_ACCEPTED));

        List<Bid> bids = bidService.findByOffer(bid.getOffer().getId());
        for (Bid currBid : bids) {
            if (currBid.getId() != bid.getId()) {
                currBid.setStatus(BidStatus.DECLINED);
                bidService.update(currBid);
                notificationService.saveAndSendNotification(new Notification(String.valueOf(currBid.getSupplier().getId()), NotificationConstants.BID_DECLINED));
            }
        }

        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/offer/{id}")
    public ResponseEntity getByOffer(@PathVariable long id) {
        List<Bid> bids = bidService.findByOffer(id);

        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAuthority('SUPPLIER')")
    @PutMapping("/{id}")
    public ResponseEntity updateBid(@PathVariable long id, @RequestBody Bid bid) {
        Bid originalBid = bidService.findById(id);
        Supplier supplier = (Supplier) authService.getCurrentUser();

        if (originalBid.getSupplier().getId() != supplier.getId()) {
            throw new ForbiddenException("Not supplier's bid!");
        }
        if (originalBid.getStatus() != BidStatus.ACTIVE) {
            throw new ForbiddenException("Bid not active!");
        }

        bid.setId(originalBid.getId());

        TransactionsUtil.sleepIfTesting(); // Optimistic locking test

        bid = bidService.update(bid);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SUPPLIER')")
    @GetMapping("/me")
    public ResponseEntity getMyBids() {
        Supplier supplier = (Supplier) authService.getCurrentUser();
        List<Bid> bids = bidService.findBySupplier(supplier.getId());

        return new ResponseEntity<>(bids, HttpStatus.OK);
    }
}
