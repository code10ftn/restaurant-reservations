package com.code10.isa.service;


import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Waiter;
import com.code10.isa.repository.UserRepository;
import com.code10.isa.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaiterService {

    private final UserRepository userRepository;
    private final WaiterRepository waiterRepository;

    @Autowired
    public WaiterService(UserRepository userRepository, WaiterRepository waiterRepository) {
        this.userRepository = userRepository;
        this.waiterRepository = waiterRepository;
    }

    public Waiter create(EmployeeRegisterDto registerDto, Restaurant restaurant) {
        final Waiter waiter = registerDto.createWaiter();
        waiter.setRestaurant(restaurant);
        return userRepository.save(waiter);
    }

    public Waiter findById(long id) {
        return waiterRepository.findById(id).orElseThrow(() -> new NotFoundException("Waiter doesn't exist!"));
    }

    public List<Waiter> findByRestaurant(long id) {
        return waiterRepository.findByRestaurantId(id);
    }
}
