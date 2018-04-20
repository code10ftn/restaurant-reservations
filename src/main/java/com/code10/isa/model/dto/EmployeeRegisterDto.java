package com.code10.isa.model.dto;

import com.code10.isa.model.Area;
import com.code10.isa.model.FoodType;
import com.code10.isa.model.user.Bartender;
import com.code10.isa.model.user.Chef;
import com.code10.isa.model.user.Waiter;

import java.util.Date;

public class EmployeeRegisterDto extends RegisterDto {

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String clothingSize;

    private int shoeSize;

    private FoodType foodType;

    private Area area;

    public EmployeeRegisterDto() {
    }

    public EmployeeRegisterDto(String email, String password, String passwordConfirmation, String firstName, String lastName, Date birthDate, String clothingSize, int shoeSize) {
        super(email, password, passwordConfirmation);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.clothingSize = clothingSize;
        this.shoeSize = shoeSize;
    }

    public Chef createChef() {
        return new Chef(firstName, lastName, email, password, birthDate, clothingSize, shoeSize, foodType);
    }

    public Waiter createWaiter() {
        return new Waiter(firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
    }

    public Bartender createBartender() {
        return new Bartender(firstName, lastName, email, password, birthDate, clothingSize, shoeSize);
    }

    public boolean fieldsPopulated() {
        return super.fieldsPopulated() && !("".equals(firstName) || "".equals(lastName) || "".equals(birthDate) || "".equals(clothingSize));
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
