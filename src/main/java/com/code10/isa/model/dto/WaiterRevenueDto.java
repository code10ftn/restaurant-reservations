package com.code10.isa.model.dto;


import com.code10.isa.model.user.Waiter;

public class WaiterRevenueDto {

    private Waiter waiter;

    private double revenue;

    public WaiterRevenueDto(Waiter waiter, double revenue) {
        this.waiter = waiter;
        this.revenue = revenue;
    }

    public WaiterRevenueDto() {
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
