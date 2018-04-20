package com.code10.isa.model.user;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class Bartender extends Employee {

    public Bartender() {
        this.setRole(Role.BARTENDER);
    }

    public Bartender(String firstName, String lastName, String email, String password, Date birthDate, String clothingSize, int shoeSize) {
        super(Role.BARTENDER, firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
    }
}
