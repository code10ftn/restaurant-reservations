package com.code10.isa.controller;

import com.code10.isa.model.Reservation;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.dto.RestaurantDto;
import com.code10.isa.model.user.Employee;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.FriendshipService;
import com.code10.isa.service.RatingService;
import com.code10.isa.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final AuthService authService;
    private final RatingService ratingService;
    private final FriendshipService friendshipService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, AuthService authService, RatingService ratingService, FriendshipService friendshipService) {
        this.restaurantService = restaurantService;
        this.authService = authService;
        this.ratingService = ratingService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity findByManager() {
        Manager manager = (Manager) authService.getCurrentUser();
        Restaurant restaurant = restaurantService.findById(manager.getRestaurant().getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyAuthority('WAITER', 'BARTENDER', 'CHEF')")
    public ResponseEntity findByEmployee() {
        final Employee employee = (Employee) authService.getCurrentUser();
        final Restaurant restaurant = restaurantService.findById(employee.getRestaurant().getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity findAll() {
        final List<Restaurant> restaurants = restaurantService.findAll();
        final List<RestaurantDto> restaurantDtos = new ArrayList<>();
        final User user = authService.getCurrentUser();

        for (Restaurant restaurant : restaurants) {
            final RestaurantDto restaurantDto = new RestaurantDto(restaurant);
            final double overall = ratingService.getOverallRating(restaurant.getId());
            restaurantDto.setOverallRating(overall);

            if (user instanceof Guest) {
                final Guest guest = (Guest) user;
                List<Guest> friends = friendshipService.findFriends(guest);
                final double friendsRating = ratingService.getFriendsRating(restaurant.getId(), friends);
                restaurantDto.setFriendsRating(friendsRating);
            }

            restaurantDtos.add(restaurantDto);
        }

        return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/visited")
    public ResponseEntity findVisited() {
        final Guest guest = (Guest) authService.getCurrentUser();
        final List<Restaurant> visited = guest.getReservationGuests().stream()
                .map(ReservationGuest::getReservation)
                .map(Reservation::getRestaurant).collect(Collectors.toList());
        return new ResponseEntity<>(visited, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody Restaurant restaurant) {
        final Restaurant newRestaurant = restaurantService.create(restaurant);
        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping // TODO Add id parameter
    public ResponseEntity update(@RequestBody Restaurant restaurant) {
        final Restaurant updatedRestaurant = restaurantService.update(restaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }
}
