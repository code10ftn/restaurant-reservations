package com.code10.isa.model.dto;

import com.code10.isa.model.Restaurant;

public class RestaurantDto {

    private long id;

    private String name;

    private String description;

    private String address;

    private double overallRating;

    private double friendsRating;

    public RestaurantDto() {
    }

    public RestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.address = restaurant.getAddress();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public double getFriendsRating() {
        return friendsRating;
    }

    public void setFriendsRating(double friendsRating) {
        this.friendsRating = friendsRating;
    }
}
