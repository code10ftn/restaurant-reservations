package com.code10.isa.model.user;

import com.code10.isa.model.Restaurant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Manager extends User {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Manager() {
        this.setRole(Role.MANAGER);
    }

    public Manager(String firstName, String lastName, String email, String password, Restaurant restaurant) {
        super(Role.MANAGER, firstName, lastName, email, password);
        this.restaurant = restaurant;
    }

    public Manager(String firstName, String lastName, String email, String password) {
        super(Role.MANAGER, firstName, lastName, email, password);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

