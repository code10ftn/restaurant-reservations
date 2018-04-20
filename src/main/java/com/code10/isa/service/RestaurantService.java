package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Menu;
import com.code10.isa.model.Restaurant;
import com.code10.isa.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }

    public Restaurant update(Restaurant updatedRestaurant) {
        final Restaurant restaurant = restaurantRepository.findById(updatedRestaurant.getId()).orElseThrow(() -> new NotFoundException("Restaurant not found!"));

        restaurant.update(updatedRestaurant);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant create(Restaurant newRestaurant) {
        if (!restaurantRepository.exists(newRestaurant.getId())) {
            Menu menu = new Menu();
            menu.setRestaurant(newRestaurant);
            newRestaurant.setMenu(menu);
            return restaurantRepository.save(newRestaurant);
        } else {
            throw new BadRequestException("Restaurant already exists!");
        }
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
