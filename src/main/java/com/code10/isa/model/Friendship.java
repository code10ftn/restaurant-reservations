package com.code10.isa.model;

import com.code10.isa.model.user.Guest;

import javax.persistence.*;

@Entity
public class Friendship {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Guest sender;

    @ManyToOne(fetch = FetchType.EAGER)
    private Guest receiver;

    private boolean accepted;

    public Friendship() {
    }

    public Friendship(Guest sender, Guest receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Guest getSender() {
        return sender;
    }

    public void setSender(Guest sender) {
        this.sender = sender;
    }

    public Guest getReceiver() {
        return receiver;
    }

    public void setReceiver(Guest receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
