package com.code10.isa.model.dto;

import java.util.List;

public class UpdateOrderDto {

    private List<UpdateOrderItemDto> orderItemDtos;

    private boolean readyAtArrival;

    public UpdateOrderDto() {
    }

    public List<UpdateOrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<UpdateOrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public boolean isReadyAtArrival() {
        return readyAtArrival;
    }

    public void setReadyAtArrival(boolean readyAtArrival) {
        this.readyAtArrival = readyAtArrival;
    }
}
