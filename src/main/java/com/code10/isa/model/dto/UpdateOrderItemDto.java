package com.code10.isa.model.dto;

import com.code10.isa.model.OrderItem;

public class UpdateOrderItemDto {

    private OrderItem orderItem;

    private boolean adding;

    public UpdateOrderItemDto() {
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }
}
