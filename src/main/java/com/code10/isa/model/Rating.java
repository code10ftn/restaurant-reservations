package com.code10.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private ReservationGuest reservationGuest;

    private int waiterRating;

    private int orderRating;

    private int restaurantRating;

    public Rating() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReservationGuest getReservationGuest() {
        return reservationGuest;
    }

    public void setReservationGuest(ReservationGuest reservationGuest) {
        this.reservationGuest = reservationGuest;
    }

    public int getWaiterRating() {
        return waiterRating;
    }

    public void setWaiterRating(int waiterRating) {
        this.waiterRating = waiterRating;
    }

    public int getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(int orderRating) {
        this.orderRating = orderRating;
    }

    public int getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(int restaurantRating) {
        this.restaurantRating = restaurantRating;
    }
}
