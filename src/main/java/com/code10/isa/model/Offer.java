package com.code10.isa.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OfferItem> offerItems;

    private Date startDate;

    private Date endDate;

    public Offer() {
    }

    public Offer(Restaurant restaurant, Date startDate, Date endDate) {
        this.restaurant = restaurant;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(Offer offer) {
        this.startDate = offer.startDate;
        this.endDate = offer.endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<OfferItem> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(Set<OfferItem> offerItems) {
        this.offerItems = offerItems;
    }
}
