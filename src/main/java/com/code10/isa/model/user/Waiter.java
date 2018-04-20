package com.code10.isa.model.user;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class Waiter extends Employee {


    public Waiter() {
        this.setRole(Role.WAITER);
    }

    public Waiter(String firstName, String lastName, String email, String password, Date birthDate, String clothingSize, int shoeSize) {
        super(Role.WAITER, firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
    }
}
