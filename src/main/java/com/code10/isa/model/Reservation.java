package com.code10.isa.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int hoursLength;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable
    private List<Table> tables;

    @OneToMany
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ReservationGuest> reservationGuests;

    public Reservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHoursLength() {
        return hoursLength;
    }

    public void setHoursLength(int hoursLength) {
        this.hoursLength = hoursLength;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<ReservationGuest> getReservationGuests() {
        return reservationGuests;
    }

    public void setReservationGuests(List<ReservationGuest> reservationGuests) {
        this.reservationGuests = reservationGuests;
    }
}
