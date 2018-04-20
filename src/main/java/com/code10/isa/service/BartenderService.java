package com.code10.isa.service;

import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Bartender;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BartenderService {

    private final UserRepository userRepository;

    @Autowired
    public BartenderService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Bartender create(EmployeeRegisterDto registerDto, Restaurant restaurant) {
        final Bartender bartender = registerDto.createBartender();
        bartender.setRestaurant(restaurant);
        return userRepository.save(bartender);
    }

}
