package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Bartender;
import com.code10.isa.model.user.Manager;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.BartenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bartenders")
public class BartenderController {

    private final BartenderService bartenderService;

    private final AuthService authService;

    @Autowired
    public BartenderController(BartenderService bartenderService, AuthService authService) {
        this.bartenderService = bartenderService;
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
        final Bartender bartender = bartenderService.create(registerDto, restaurant);
        return new ResponseEntity<>(bartender, HttpStatus.CREATED);
    }
}
