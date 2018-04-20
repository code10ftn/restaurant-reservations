package com.code10.isa.model.dto;

import com.code10.isa.model.Area;
import com.code10.isa.model.Restaurant;

public class AreaDto {

    private Area area;

    private Restaurant restaurant;

    public AreaDto() {
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
