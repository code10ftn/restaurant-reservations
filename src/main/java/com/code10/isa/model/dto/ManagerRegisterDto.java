package com.code10.isa.model.dto;

import com.code10.isa.model.user.Manager;

public class ManagerRegisterDto extends RegisterDto {

    private String firstName;

    private String lastName;

    private long restaurantId;

    public ManagerRegisterDto() {
    }

    public ManagerRegisterDto(String email, String password, String passwordConfirmation, String firstName, String lastName, long restaurantId) {
        super(email, password, passwordConfirmation);
        this.firstName = firstName;
        this.lastName = lastName;
        this.restaurantId = restaurantId;
    }

    public Manager createManager() {
        return new Manager(firstName, lastName, email, password);
    }

    public boolean fieldsPopulated() {
        return super.fieldsPopulated() && !("".equals(firstName) || "".equals(lastName) || "".equals(restaurantId));
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

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
