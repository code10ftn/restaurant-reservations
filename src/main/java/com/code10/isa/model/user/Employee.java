package com.code10.isa.model.user;

import com.code10.isa.model.Restaurant;
import com.code10.isa.model.Shift;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {

    protected Date birthDate;

    protected String clothingSize;

    protected int shoeSize;

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    protected List<Shift> shiftList;

    public Employee() {
    }

    public Employee(Role role, String firstName, String lastName, String email, String password, Date birthDate, String clothingSize, int shoeSize) {
        super(role, firstName, lastName, email, password);
        this.birthDate = birthDate;
        this.clothingSize = clothingSize;
        this.shoeSize = shoeSize;
    }

    public void update(Employee employee) {
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.clothingSize = employee.clothingSize;
        this.shoeSize = employee.shoeSize;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getClothingSize() {
        return clothingSize;
    }

    public void setClothingSize(String clothingSize) {
        this.clothingSize = clothingSize;
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
