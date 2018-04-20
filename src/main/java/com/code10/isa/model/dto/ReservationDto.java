package com.code10.isa.model.dto;


import com.code10.isa.model.Reservation;
import com.code10.isa.model.user.Guest;

import java.util.List;

public class ReservationDto {

    private Reservation reservation;

    private List<Guest> guests;

    private OrderDto order;

    public ReservationDto() {
    }

    public ReservationDto(Reservation reservation, List<Guest> guests) {
        this.reservation = reservation;
        this.guests = guests;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }
}
