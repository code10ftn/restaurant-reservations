package com.code10.isa.model.dto;

public class ReportRatingDto {

    private double rating;
    private int count;

    public ReportRatingDto() {
    }

    public ReportRatingDto(double rating, int count) {
        this.rating = rating;
        this.count = count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
