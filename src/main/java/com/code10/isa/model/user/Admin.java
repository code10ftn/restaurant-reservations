package com.code10.isa.model.user;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
        this.setRole(Role.ADMIN);
    }

    public Admin(String email, String password) {
        super(Role.ADMIN, email, password);
    }
}
