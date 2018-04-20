package com.code10.isa.model;

import com.code10.isa.model.user.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Shift {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Enumerated(EnumType.STRING)
    private Area area;

    public Shift() {
    }

    public Shift(Employee employee, Restaurant restaurant, Date startTime, Date endTime) {
        this.employee = employee;
        this.restaurant = restaurant;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
