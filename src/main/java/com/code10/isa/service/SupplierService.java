package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.SupplierRegisterDto;
import com.code10.isa.model.user.Supplier;
import com.code10.isa.repository.SupplierRepository;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final UserRepository userRepository;

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(UserRepository userRepository, SupplierRepository supplierRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    public Supplier create(SupplierRegisterDto registerDto, Restaurant restaurant) {
        Supplier supplier = registerDto.createSupplier();
        supplier.setRestaurant(restaurant);
        return userRepository.save(supplier);
    }

    public Supplier update(long id, Supplier updated, Supplier currentSupplier) {
        final Supplier supplier = (Supplier) userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));

        if (currentSupplier.getId() != supplier.getId()) {
            throw new ForbiddenException();
        }

        if (supplier.getId() != updated.getId()) {
            throw new BadRequestException("Wrong user id!");
        }

        supplier.update(updated);
        return userRepository.save(supplier);
    }

    public List<Supplier> findByRestaurant(long id) {
        return supplierRepository.findByRestaurantId(id);
    }
}
