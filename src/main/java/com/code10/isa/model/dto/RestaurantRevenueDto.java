package com.code10.isa.model.dto;

import java.util.Date;

public class RestaurantRevenueDto {

    private Date startDate;

    private Date endDate;


    private double revenue;

    public RestaurantRevenueDto() {
    }

    public RestaurantRevenueDto(Date startDate, Date endDate, double revenue) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.revenue = revenue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
