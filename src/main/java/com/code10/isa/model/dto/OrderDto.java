package com.code10.isa.model.dto;

import com.code10.isa.model.Order;
import com.code10.isa.model.OrderItem;
import com.code10.isa.model.OrderItemStatus;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private com.code10.isa.model.Table table;

    private List<OrderItem> orderItems;

    private boolean readyAtArrival;

    public OrderDto() {
    }

    public Order createOrder() {
        final Order order = new Order();
        for (OrderItem orderItem : orderItems) {
            orderItem.setStatus(OrderItemStatus.CREATED);
        }
        order.setOrderItems(orderItems);
        order.setReadyAtArrival(readyAtArrival);
        order.setTable(table);
        order.setCreated(new Date());
        return order;
    }

    public com.code10.isa.model.Table getTable() {
        return table;
    }

    public void setTable(com.code10.isa.model.Table table) {
        this.table = table;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isReadyAtArrival() {
        return readyAtArrival;
    }

    public void setReadyAtArrival(boolean readyAtArrival) {
        this.readyAtArrival = readyAtArrival;
    }
}
