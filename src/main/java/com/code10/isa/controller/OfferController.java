package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Offer;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Supplier;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    private final AuthService authService;

    @Autowired
    public OfferController(OfferService offerService, AuthService authService) {
        this.offerService = offerService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity create(@RequestBody Offer offer) {

        if (offer.getStartDate().before(new Date())) {
            throw new BadRequestException("Start date must be after current date.");
        }

        if (offer.getEndDate().before(offer.getStartDate())) {
            throw new BadRequestException("End date is before start date.");
        }

        Manager manager = (Manager) authService.getCurrentUser();
        offer.setRestaurant(manager.getRestaurant());
        Offer createdOffer = offerService.create(offer);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity findByRestaurant(@PathVariable long id) {
        return new ResponseEntity<>(offerService.findByRestaurant(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SUPPLIER')")
    @GetMapping("/my-restaurant")
    public ResponseEntity findByMyRestaurant() {
        long restaurantId = -1;

        User user = authService.getCurrentUser();
        if (user instanceof Manager) {
            restaurantId = ((Manager) user).getRestaurant().getId();
        } else if (user instanceof Supplier) {
            restaurantId = ((Supplier) user).getRestaurant().getId();
        }

        return new ResponseEntity<>(offerService.findByRestaurant(restaurantId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SUPPLIER')")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Offer offer = offerService.findById(id);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable long id, @RequestBody Offer offer) {

        if (offer.getEndDate().before(offer.getStartDate())) {
            throw new BadRequestException("End date is before start date.");
        }

        final Offer updatedOffer = offerService.update(id, offer);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }
}
