package com.code10.isa.controller;

import com.code10.isa.model.Area;
import com.code10.isa.model.Shift;
import com.code10.isa.model.user.Employee;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.RestaurantService;
import com.code10.isa.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;
    private final AuthService authService;
    private final RestaurantService restaurantService;

    @Autowired
    public ShiftController(ShiftService shiftService, AuthService authService, RestaurantService restaurantService) {
        this.shiftService = shiftService;
        this.authService = authService;
        this.restaurantService = restaurantService;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'WAITER', 'BARTENDER', 'CHEF')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Shift>> getAllShifts(@PathVariable long restaurantId) {
        final List<Shift> shifts = shiftService.findAll(restaurantId);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<Shift> addShift(@RequestBody Shift shift, @RequestParam(required = false) Area area) {
        final Shift createdShift = shiftService.create(shift, area);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Shift> updateShift(@PathVariable long id, @RequestBody Shift shift) {
        final Shift updatedShift = shiftService.update(id, shift);
        return new ResponseEntity<>(updatedShift, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping
    public ResponseEntity updateAllShifts(@RequestBody List<Shift> shifts) {
        for (Shift shift : shifts) {
            shiftService.update(shift.getId(), shift);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteShift(@PathVariable long id) {
        final Shift deletedShift = shiftService.delete(id);
        return new ResponseEntity<>(deletedShift.getId(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('WAITER', 'CHEF', 'BARTENDER')")
    @GetMapping("/me")
    public ResponseEntity<Shift> findCurrentShift() {
        final Employee currentEmployee = (Employee) authService.getCurrentUser();
        final Shift shift = shiftService.findCurrent(new Date(), currentEmployee);
        return new ResponseEntity<>(shift, HttpStatus.OK);
    }
}
