package com.code10.isa.model;

import com.code10.isa.model.user.Supplier;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.EAGER)
    private Offer offer;

    private double price;

    private String warranty;

    private Date dateOfDelivery;

    @Enumerated(EnumType.STRING)
    private BidStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;

    public Bid() {
        this.status = BidStatus.ACTIVE;
    }

    public Bid(Offer offer, double price, Date dateOfDelivery, Supplier supplier) {
        this.offer = offer;
        this.price = price;
        this.dateOfDelivery = dateOfDelivery;
        this.supplier = supplier;
        this.status = BidStatus.ACTIVE;
    }

    public void update(Bid bid) {
        this.price = bid.price;
        this.dateOfDelivery = bid.dateOfDelivery;
        this.status = bid.status;
        this.warranty = bid.warranty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(Date dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }
}
