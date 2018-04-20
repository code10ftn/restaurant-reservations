package com.code10.isa.model.dto;


import com.code10.isa.model.user.Supplier;

public class SupplierRegisterDto extends RegisterDto {

    private String firstName;

    private String lastName;

    public SupplierRegisterDto() {
    }

    public SupplierRegisterDto(String email, String password, String passwordConfirmation, String firstName, String lastName) {
        super(email, password, passwordConfirmation);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Supplier createSupplier() {
        return new Supplier(firstName, lastName, email, password);
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
