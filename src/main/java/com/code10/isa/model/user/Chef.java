package com.code10.isa.model.user;

import com.code10.isa.model.FoodType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;


@Entity
public class Chef extends Employee {

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    public Chef() {
        this.setRole(Role.CHEF);
    }

    public Chef(String firstName, String lastName, String email, String password, Date birthDate, String clothingSize, int shoeSize) {
        super(Role.CHEF, firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
    }

    public Chef(String firstName, String lastName, String email, String password, Date birthDate, String clothingSize, int shoeSize, FoodType foodType) {
        super(Role.CHEF, firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
        this.foodType = foodType;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
