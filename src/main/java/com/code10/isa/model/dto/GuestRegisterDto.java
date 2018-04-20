package com.code10.isa.model.dto;

import com.code10.isa.model.user.Guest;

public class GuestRegisterDto extends RegisterDto {

    private String firstName;

    private String lastName;

    public GuestRegisterDto() {
    }

    public GuestRegisterDto(String email, String password, String passwordConfirmation, String firstName, String lastName) {
        super(email, password, passwordConfirmation);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Guest createGuest() {
        return new Guest(firstName, lastName, email, password);
    }

    public boolean fieldsPopulated() {
        return super.fieldsPopulated() && !("".equals(firstName) || "".equals(lastName));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
