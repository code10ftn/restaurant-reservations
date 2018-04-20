package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Chef;
import com.code10.isa.model.user.Manager;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chefs")
public class ChefController {

    private final ChefService chefService;

    private final AuthService authService;

    @Autowired
    public ChefController(ChefService chefService, AuthService authService) {
        this.chefService = chefService;
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
        final Chef chef = chefService.create(registerDto, restaurant);
        return new ResponseEntity<>(chef, HttpStatus.CREATED);
    }

}
