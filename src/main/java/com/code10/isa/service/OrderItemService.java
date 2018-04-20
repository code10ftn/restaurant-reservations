package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.*;
import com.code10.isa.model.user.Bartender;
import com.code10.isa.model.user.Chef;
import com.code10.isa.model.user.Employee;
import com.code10.isa.model.user.Role;
import com.code10.isa.repository.OrderItemRepository;
import com.code10.isa.repository.OrderRepository;
import com.code10.isa.util.DateUtil;
import com.code10.isa.util.TransactionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItem> findAll(Employee employee) {
        final List<Order> orders = orderRepository.findByRestaurantId(employee.getRestaurant().getId());
        final List<OrderItem> items = new ArrayList<>();

        for (Order order : orders) {

            if (order.getWaiter() == null) {
                continue;
            }

            if (order.getReservation() != null && DateUtil.getDifferenceInMinutes(order.getReservation().getDate(), new Date()) >= 30) {
                continue;
            }

            for (OrderItem orderItem : order.getOrderItems()) {
                if (employee.getRole() == Role.CHEF) {
                    if (orderItem.getStatus() == OrderItemStatus.CREATED && orderItem.getMenuItem().getType() == MenuItemType.FOOD) {
                        items.add(orderItem);
                    }
                } else {
                    if (orderItem.getStatus() == OrderItemStatus.CREATED && orderItem.getMenuItem().getType() == MenuItemType.DRINK) {
                        items.add(orderItem);
                    }
                }
            }
        }

        return items;
    }

    public List<OrderItem> findAllByChef(Chef chef) {
        final List<OrderItem> orderItems = orderItemRepository.findByChefId(chef.getId());
        final List<OrderItem> items = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getStatus() == OrderItemStatus.PREPARING) {
                items.add(orderItem);
            }
        }

        return items;
    }

    public List<OrderItem> findAllByBartender(Bartender bartender) {
        final List<OrderItem> orderItems = orderItemRepository.findByBartenderId(bartender.getId());
        final List<OrderItem> items = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getStatus() == OrderItemStatus.PREPARING) {
                items.add(orderItem);
            }
        }

        return items;
    }

    @Transactional
    public OrderItem update(long id, OrderItemStatus status, Chef chef) {
        final OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Order item doesn't exist!"));

        TransactionsUtil.sleepIfTesting();

        if (orderItem.getChef() != null && orderItem.getChef().getId() != chef.getId()) {
            throw new BadRequestException("Order item is already being prepared!");
        }

        orderItem.setStatus(status);
        if (status == OrderItemStatus.PREPARING) {
            orderItem.setChef(chef);
            final Order order = orderItem.getOrder();
            order.setOrderStatus(OrderStatus.PREPARING);
            orderRepository.save(order);
        }

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem update(long id, OrderItemStatus status, Bartender bartender) {
        final OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new NotFoundException("OrderItem doesn't exist!"));

        TransactionsUtil.sleepIfTesting();

        if (orderItem.getBartender() != null && orderItem.getBartender().getId() != bartender.getId()) {
            throw new BadRequestException("Order item is already being prepared!");
        }

        orderItem.setStatus(status);
        if (status == OrderItemStatus.PREPARING) {
            orderItem.setBartender(bartender);
            final Order order = orderItem.getOrder();
            order.setOrderStatus(OrderStatus.PREPARING);
            orderRepository.save(order);
        }

        return orderItemRepository.save(orderItem);
    }
}
