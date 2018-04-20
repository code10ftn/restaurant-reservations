package com.code10.isa.service;

import com.code10.isa.model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Test
    public void findAllShouldReturnListOfRestaurants() {

        final List<Restaurant> createdTestEntity = restaurantService.findAll();
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.size()).isGreaterThan(0);
    }

    @Test
    public void findByIdWithValidDataShouldReturnRestaurant() {

        final Restaurant createdTestEntity = restaurantService.findById(EXISTING_RESTAURANT_ID);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getId()).isEqualTo(EXISTING_RESTAURANT_ID);
    }

    @Test
    public void createWithValidDataShouldReturnSavedRestaurant() {
        final Restaurant restaurant = new Restaurant("test name", "test desc", "test address");
        final Restaurant createdRestaurant = restaurantService.create(restaurant);

        assertThat(createdRestaurant).isNotNull();

        assertThat(createdRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(createdRestaurant.getName()).isEqualTo(restaurant.getName());
    }

    @Test
    public void updateWithValidDataShouldReturnUpdatedRestaurant() {
        final Restaurant restaurant = new Restaurant("test name", "test desc", "test address");
        restaurant.setId(EXISTING_RESTAURANT_ID);

        final Restaurant updatedRestaurant = restaurantService.update(restaurant);

        assertThat(updatedRestaurant).isNotNull();
        assertThat(updatedRestaurant.getId()).isEqualTo(restaurant.getId());
        assertThat(updatedRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(updatedRestaurant.getName()).isEqualTo(restaurant.getName());

    }
}
