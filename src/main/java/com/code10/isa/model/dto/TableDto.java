package com.code10.isa.model.dto;

import com.code10.isa.model.Area;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.Table;

public class TableDto {

    private long id;

    private Restaurant restaurant;

    private Area area;

    private double horizontalPosition;

    private double verticalPosition;

    private int seatCount;

    private boolean available;

    private boolean selected;

    public TableDto() {
    }

    public TableDto(Table table) {
        this.id = table.getId();
        this.restaurant = table.getRestaurant();
        this.area = table.getArea();
        this.horizontalPosition = table.getHorizontalPosition();
        this.verticalPosition = table.getVerticalPosition();
        this.seatCount = table.getSeatCount();
        this.available = true;
        this.selected = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
