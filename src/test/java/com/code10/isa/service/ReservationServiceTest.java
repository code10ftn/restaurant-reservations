package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.*;
import com.code10.isa.repository.RestaurantRepository;
import com.code10.isa.repository.TableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private TableRepository tableRepository;

    @Test
    public void createWithValidDataShouldReturnSavedReservation() {
        final Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(NotFoundException::new);
        final Table table = tableRepository.findById(1).orElseThrow(NotFoundException::new);
        final List<Table> tables = new ArrayList<>();
        tables.add(table);

        final Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setTables(tables);
        reservation.setDate(new Date());
        reservation.setHoursLength(2);

        final Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);

        final Reservation createdReservation = reservationService.create(reservation, order);

        assertThat(createdReservation).isNotNull();
    }
}
