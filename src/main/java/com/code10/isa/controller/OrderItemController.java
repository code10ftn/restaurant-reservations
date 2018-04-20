package com.code10.isa.controller;

import com.code10.isa.model.OrderItem;
import com.code10.isa.model.dto.OrderItemDto;
import com.code10.isa.model.user.Bartender;
import com.code10.isa.model.user.Chef;
import com.code10.isa.model.user.Employee;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.OrderItemService;
import com.code10.isa.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final AuthService authService;
    private final ShiftService shiftService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, AuthService authService, ShiftService shiftService) {
        this.orderItemService = orderItemService;
        this.authService = authService;
        this.shiftService = shiftService;
    }

    @PreAuthorize("hasAnyAuthority('CHEF', 'BARTENDER')")
    @GetMapping
    public ResponseEntity<List<OrderItem>> findAll() {
        final Employee currentEmployee = (Employee) authService.getCurrentUser();
        shiftService.findCurrent(new Date(), currentEmployee);
        final List<OrderItem> items = orderItemService.findAll(currentEmployee);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CHEF')")
    @GetMapping("/chef")
    public ResponseEntity<List<OrderItem>> findAllByChef() {
        final Chef currentChef = (Chef) authService.getCurrentUser();
        shiftService.findCurrent(new Date(), currentChef);
        final List<OrderItem> orderItems = orderItemService.findAllByChef(currentChef);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BARTENDER')")
    @GetMapping("/bartender")
    public ResponseEntity<List<OrderItem>> findAllByBartender() {
        final Bartender currentBartender = (Bartender) authService.getCurrentUser();
        shiftService.findCurrent(new Date(), currentBartender);
        final List<OrderItem> orderItems = orderItemService.findAllByBartender(currentBartender);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CHEF')")
    @PutMapping("/{id}/chef")
    public ResponseEntity<OrderItem> updateChef(@PathVariable long id, @RequestBody OrderItemDto itemDto) {
        final Chef currentChef = (Chef) authService.getCurrentUser();
        shiftService.findCurrent(new Date(), currentChef);
        final OrderItem orderItem = orderItemService.update(id, itemDto.getOrderItemStatus(), currentChef);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BARTENDER')")
    @PutMapping("/{id}/bartender")
    public ResponseEntity<OrderItem> updateBartender(@PathVariable long id, @RequestBody OrderItemDto itemDto) {
        final Bartender currentBartender = (Bartender) authService.getCurrentUser();
        shiftService.findCurrent(new Date(), currentBartender);
        final OrderItem orderItem = orderItemService.update(id, itemDto.getOrderItemStatus(), currentBartender);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }
}
