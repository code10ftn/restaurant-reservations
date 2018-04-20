package com.code10.isa.repository;

import com.code10.isa.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);

    Optional<Restaurant> findById(Long id);
}
