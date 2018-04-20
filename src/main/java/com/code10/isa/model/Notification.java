package com.code10.isa.model;

import com.code10.isa.model.user.Guest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Guest sender;

    private String topic;

    private String message;

    private Date date = new Date();

    public Notification() {
    }

    public Notification(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public Notification(Guest sender, String topic, String message) {
        this.sender = sender;
        this.topic = topic;
        this.message = String.format("%s %s %s", sender.getFirstName(), sender.getLastName(), message);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getSender() {
        return sender;
    }

    public void setSender(Guest sender) {
        this.sender = sender;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
