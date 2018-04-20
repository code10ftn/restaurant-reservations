package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.ManagerRegisterDto;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Role;
import com.code10.isa.repository.RestaurantRepository;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ManagerService(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Manager create(ManagerRegisterDto registerDto) {
        final Manager manager = registerDto.createManager();
        long id = registerDto.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));

        manager.setRestaurant(restaurant);
        manager.setVerified(true);
        return userRepository.save(manager);
    }

    public List<Manager> findAll() {
        return userRepository.findByRole(Role.MANAGER).stream().map(user -> (Manager) user).collect(Collectors.toList());
    }
}
