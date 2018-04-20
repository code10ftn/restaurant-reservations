package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.dto.ManagerRegisterDto;
import com.code10.isa.model.user.Manager;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    private final AuthService authService;

    @Autowired
    public ManagerController(ManagerService managerService, AuthService authService) {
        this.managerService = managerService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody ManagerRegisterDto registerDto) {
        if (authService.emailTaken(registerDto)) {
            throw new BadRequestException("Email taken!");
        }

        if (!registerDto.fieldsPopulated()) {
            throw new BadRequestException("Please enter all fields!");
        }

        if (!registerDto.passwordsMatch()) {
            throw new BadRequestException("Passwords not matching!");
        }

        final Manager manager = managerService.create(registerDto);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity findALl() {
        List<Manager> managers = managerService.findAll();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }
}
