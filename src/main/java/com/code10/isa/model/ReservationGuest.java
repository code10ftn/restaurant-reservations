package com.code10.isa.model;

import com.code10.isa.model.user.Guest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ReservationGuest {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    private Guest guest;

    @OneToOne(fetch = FetchType.EAGER)
    private Rating rating;

    private boolean isHost;

    private boolean accepted;

    private String invitationCode;

    public ReservationGuest() {
        this.invitationCode = UUID.randomUUID().toString();
    }

    public ReservationGuest(Reservation reservation, Guest guest, boolean isHost, boolean accepted) {
        this.reservation = reservation;
        this.guest = guest;
        this.isHost = isHost;
        this.accepted = accepted;
        this.invitationCode = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
