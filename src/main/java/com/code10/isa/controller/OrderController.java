package com.code10.isa.controller;

import com.code10.isa.model.Notification;
import com.code10.isa.model.Order;
import com.code10.isa.model.OrderStatus;
import com.code10.isa.model.Shift;
import com.code10.isa.model.dto.OrderDto;
import com.code10.isa.model.dto.UpdateOrderDto;
import com.code10.isa.model.user.Waiter;
import com.code10.isa.service.*;
import com.code10.isa.util.DateUtil;
import com.code10.isa.util.NotificationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;
    private final NotificationService notificationService;
    private final ShiftService shiftService;
    private final WaiterService waiterService;

    @Autowired
    public OrderController(OrderService orderService, AuthService authService, NotificationService notificationService, ShiftService shiftService, WaiterService waiterService) {
        this.orderService = orderService;
        this.authService = authService;
        this.notificationService = notificationService;
        this.shiftService = shiftService;
        this.waiterService = waiterService;
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @GetMapping("/waiter")
    public ResponseEntity<List<Order>> findAll() {
        final Waiter waiter = (Waiter) authService.getCurrentUser();
        final Shift currentShift = shiftService.findCurrent(new Date(), waiter);

        final List<Order> orderList = orderService.findAll();
        final List<Order> orders = new ArrayList<>();

        for (Order order : orderList) {
            if (order.getOrderStatus() != OrderStatus.FINISHED && order.getWaiter() != null && order.getWaiter().getId() == waiter.getId()) {
                orders.add(order);
            } else if (order.getOrderStatus() != OrderStatus.FINISHED && order.getWaiter() != null && order.getArea() == currentShift.getArea()) {
                orders.add(order);
            }
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @GetMapping("/unaccepted")
    public ResponseEntity<List<Order>> findAllUnacceptedOrders() {
        final Waiter waiter = (Waiter) authService.getCurrentUser();
        final Shift currentShift = shiftService.findCurrent(new Date(), waiter);

        final List<Order> orderList = orderService.findAll();
        final List<Order> orders = new ArrayList<>();

        for (Order order : orderList) {
            if (order.getReservation() != null && order.getWaiter() == null && shiftService.isOrderInArea(order.getReservation().getTables(), currentShift.getArea())) {
                if (DateUtil.getDifferenceInMinutes(order.getReservation().getDate(), new Date()) < 30) {
                    orders.add(order);
                }
            }
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable long id) {
        final Order order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDto orderDto) {
        final Waiter currentWaiter = (Waiter) authService.getCurrentUser();
        final Shift shift = shiftService.findCurrent(new Date(), currentWaiter);
        final Order order = orderService.create(orderDto, currentWaiter, shift.getArea());
        notificationService.saveAndSendNotification(new Notification("ordersFood", NotificationConstants.NEW_FOOD_ORDER));
        notificationService.saveAndSendNotification(new Notification("ordersDrinks", NotificationConstants.NEW_DRINK_ORDER));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('WAITER', 'GUEST')")
    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable long id, @RequestBody UpdateOrderDto updateOrderDto) {
        final Order updatedOrder = orderService.update(id, updateOrderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping("/{id}/accept")
    public ResponseEntity<Order> acceptOrder(@PathVariable long id) {
        final Waiter waiter = (Waiter) authService.getCurrentUser();
        final Shift shift = shiftService.findCurrent(new Date(), waiter);   //throws bad request if not found
        final Order order = orderService.accept(id, waiter, shift.getArea());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping("/{id}/close")
    public ResponseEntity<Order> closeOrder(@PathVariable long id, @RequestParam long waiterId) {
        final Order order = orderService.findById(id);
        final Shift originalWaiterShift = shiftService.findCurrent(order.getAccepted(), order.getWaiter());
        final Shift newWaiterShift = shiftService.findCurrent(new Date(), waiterService.findById(waiterId));

        final Order closedOrder = orderService.close(id, waiterId, originalWaiterShift, newWaiterShift);
        return new ResponseEntity<>(closedOrder, HttpStatus.OK);
    }
}
