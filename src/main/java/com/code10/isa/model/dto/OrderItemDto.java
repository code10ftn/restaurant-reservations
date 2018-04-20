package com.code10.isa.model.dto;

import com.code10.isa.model.OrderItemStatus;

public class OrderItemDto {

    private OrderItemStatus orderItemStatus;

    public OrderItemDto() {
    }

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }
}
