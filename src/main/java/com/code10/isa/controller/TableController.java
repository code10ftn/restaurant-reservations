package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.Shift;
import com.code10.isa.model.Table;
import com.code10.isa.model.dto.TableDto;
import com.code10.isa.model.user.Employee;
import com.code10.isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;
    private final RestaurantService restaurantService;
    private final ShiftService shiftService;
    private final AuthService authService;
    private final ReservationService reservationService;

    @Autowired
    public TableController(TableService tableService, RestaurantService restaurantService, ShiftService shiftService, AuthService authService, ReservationService reservationService) {
        this.tableService = tableService;
        this.restaurantService = restaurantService;
        this.shiftService = shiftService;
        this.authService = authService;
        this.reservationService = reservationService;
    }

    @GetMapping("/restaurant/{id}")
    ResponseEntity<List<Table>> getTablesByRestaurantId(@PathVariable long id) {
        final Restaurant restaurant = restaurantService.findById(id);
        final List<Table> tables = tableService.findByRestaurant(restaurant);

        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/available")
    @Transactional
    ResponseEntity<List<TableDto>> getAvailableTablesByRestaurantId
            (@PathVariable long id,
             @RequestParam @DateTimeFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss z") Date reservationDate,
             @RequestParam(required = false) Integer reservationLength) {
        if (reservationLength == null) {
            reservationLength = 0;
        }
        final Restaurant restaurant = restaurantService.findById(id);
        final List<TableDto> tables = tableService.findAvailableByRestaurant(restaurant, reservationDate, reservationLength);

        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @PostMapping("/restaurant")
    ResponseEntity<List<TableDto>> getTablesByArea(@RequestBody Restaurant restaurant) {
        final Shift currentShift = shiftService.findCurrent(new Date(), (Employee) authService.getCurrentUser());
        final List<TableDto> tables = tableService.findByArea(currentShift.getArea(), restaurant);
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<Table> update(@PathVariable long id, @RequestBody Table table) {
        table.setId(id);
        Table updatedTable = tableService.update(table);

        return new ResponseEntity<>(updatedTable, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity<Table> delete(@PathVariable long id) {

        if (!reservationService.findByTable(id).isEmpty()) {
            throw new BadRequestException("Table is reserved!");
        }

        tableService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    ResponseEntity<Table> create(@RequestBody Table table) {
        Restaurant restaurant = restaurantService.findById(table.getRestaurant().getId());
        Table updatedTable = tableService.create(table, restaurant);

        return new ResponseEntity<>(updatedTable, HttpStatus.CREATED);
    }
}
