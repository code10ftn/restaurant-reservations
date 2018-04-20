package com.code10.isa.model.user;

import com.code10.isa.model.Restaurant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Supplier extends User {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Supplier() {
        this.setRole(Role.SUPPLIER);
    }

    public Supplier(String firstName, String lastName, String email, String password) {
        super(Role.SUPPLIER, firstName, lastName, email, password);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void update(Supplier supplier) {
        this.firstName = supplier.firstName;
        this.lastName = supplier.lastName;
    }
}
