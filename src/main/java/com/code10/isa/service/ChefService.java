package com.code10.isa.service;

import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Chef;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChefService {

    private final UserRepository userRepository;

    @Autowired
    public ChefService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Chef create(EmployeeRegisterDto registerDto, Restaurant restaurant) {
        final Chef chef = registerDto.createChef();
        chef.setRestaurant(restaurant);
        return userRepository.save(chef);
    }
}
