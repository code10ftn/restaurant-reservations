package com.code10.isa.model.user;

import com.code10.isa.model.ReservationGuest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Guest extends User {

    private String verificationCode;

    @JsonIgnore
    @OneToMany(mappedBy = "guest")
    private List<ReservationGuest> reservationGuests;

    public Guest() {
        this.setRole(Role.GUEST);
        this.verificationCode = UUID.randomUUID().toString();
    }

    public Guest(String firstName, String lastName, String email, String password) {
        super(Role.GUEST, firstName, lastName, email, password);
        this.verificationCode = UUID.randomUUID().toString();
    }

    public void update(Guest guest) {
        this.firstName = guest.firstName;
        this.lastName = guest.lastName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public List<ReservationGuest> getReservationGuests() {
        return reservationGuests;
    }

    public void setReservationGuests(List<ReservationGuest> reservationGuests) {
        this.reservationGuests = reservationGuests;
    }
}
