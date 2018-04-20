package com.code10.isa.controller;

import com.code10.isa.model.Restaurant;
import com.code10.isa.model.user.Role;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    private static final String BASE_URL = "/api/restaurants";

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getAllRestaurantShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void getRestaurantByIdWithValidIdShouldReturnOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + EXISTING_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void createWithValidDataShouldReturnCreated() throws Exception {
        LoginUtil.login(mockMvc, Role.ADMIN);

        final Restaurant restaurant = new Restaurant("test name", "test desc", "test address");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(restaurant)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void updateWithValidDataShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        final Restaurant restaurant = new Restaurant("test name", "test desc", "test address");
        restaurant.setId(EXISTING_RESTAURANT_ID);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(restaurant)))
                .andExpect(status().isOk());
    }
}
