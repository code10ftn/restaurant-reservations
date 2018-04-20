package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Waiter;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiters")
public class WaiterController {

    private final WaiterService waiterService;

    private final AuthService authService;

    @Autowired
    public WaiterController(WaiterService waiterService, AuthService authService) {
        this.waiterService = waiterService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity create(@RequestBody EmployeeRegisterDto registerDto) {
        if (authService.emailTaken(registerDto)) {
            throw new BadRequestException("Email taken!");
        }

        if (!registerDto.fieldsPopulated()) {
            throw new BadRequestException("Please enter all fields!");
        }

        if (!registerDto.passwordsMatch()) {
            throw new BadRequestException("Passwords not matching!");
        }

        Manager manager = (Manager) authService.getCurrentUser();
        Restaurant restaurant = manager.getRestaurant();
        final Waiter waiter = waiterService.create(registerDto, restaurant);
        return new ResponseEntity<>(waiter, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/myWaiters")
    public ResponseEntity findByRestaurant() {

        Manager manager = (Manager) authService.getCurrentUser();

        List<Waiter> waiters = waiterService.findByRestaurant(manager.getRestaurant().getId());

        return new ResponseEntity<>(waiters, HttpStatus.OK);
    }
}
