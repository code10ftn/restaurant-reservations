package com.code10.isa.controller;

import com.code10.isa.model.Reservation;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.Table;
import com.code10.isa.model.dto.OrderDto;
import com.code10.isa.model.dto.ReservationDto;
import com.code10.isa.model.user.Role;
import com.code10.isa.service.RestaurantService;
import com.code10.isa.service.TableService;
import com.code10.isa.util.JsonUtil;
import com.code10.isa.util.LoginUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class ReservationControllerTest {

    private static final String BASE_URL = "/api/reservations";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private TableService tableService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void createWithValidDataShouldReturnCreated() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);

        final Restaurant restaurant = restaurantService.findById(1);
        final Table table = tableService.findById(1);
        final List<Table> tables = new ArrayList<>();
        tables.add(table);

        final Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setTables(tables);
        reservation.setDate(new Date());
        reservation.setHoursLength(2);

        final OrderDto orderDto = new OrderDto();
        orderDto.setOrderItems(new ArrayList<>());

        final ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservation(reservation);
        reservationDto.setOrder(orderDto);
        reservationDto.setGuests(new ArrayList<>());

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(reservationDto)))
                .andExpect(status().isCreated());
    }
}
