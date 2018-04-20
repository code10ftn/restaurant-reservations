package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.SupplierRegisterDto;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Supplier;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final AuthService authService;

    @Autowired
    public SupplierController(SupplierService supplierService, AuthService authService) {
        this.supplierService = supplierService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    ResponseEntity create(@RequestBody SupplierRegisterDto registerDto) {
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
        final Supplier supplier = supplierService.create(registerDto, restaurant);
        return new ResponseEntity<>(supplier, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SUPPLIER')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Supplier supplier) {
        final Supplier currSupplier = (Supplier) authService.getCurrentUser();
        final Supplier updatedSupplier = supplierService.update(id, supplier, currSupplier);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/me")
    public ResponseEntity findAllForRestaurant() {
        final Manager manager = (Manager) authService.getCurrentUser();
        final List<Supplier> suppliers = supplierService.findByRestaurant(manager.getRestaurant().getId());
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }
}
