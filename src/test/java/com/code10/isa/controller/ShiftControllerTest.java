package com.code10.isa.controller;

import com.code10.isa.model.Shift;
import com.code10.isa.model.user.Role;
import com.code10.isa.service.EmployeeService;
import com.code10.isa.service.RestaurantService;
import com.code10.isa.service.ShiftService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class ShiftControllerTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    ShiftService shiftService;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/shifts";

    private static final long EXISTING_SHIFT_ID = 1;

    private static final long EXISTING_EMPLOYEE_ID = 3;

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Test
    @WithMockUser
    public void getAllShiftsShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.BARTENDER);

        mockMvc.perform(get(BASE_URL + "/restaurant/" + EXISTING_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void addShiftShouldReturnCreated() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        final Shift shift = new Shift(employeeService.findById(EXISTING_EMPLOYEE_ID),
                restaurantService.findById(EXISTING_RESTAURANT_ID), new Date(), new Date());

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(shift)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void updateShiftShouldReturnUpdatedShift() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        final Shift shift = new Shift(employeeService.findById(EXISTING_EMPLOYEE_ID),
                restaurantService.findById(EXISTING_RESTAURANT_ID), new Date(), new Date());
        shift.setId(EXISTING_SHIFT_ID);

        mockMvc.perform(put(BASE_URL + "/" + EXISTING_SHIFT_ID)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(shift)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void updateAllShiftsShouldReturnUpdatedShift() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        final Shift shift = shiftService.findById(EXISTING_SHIFT_ID);
        final List<Shift> shiftList = new ArrayList<>();
        shiftList.add(shift);

        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(shiftList)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteShiftShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        mockMvc.perform(delete(BASE_URL + "/" + EXISTING_SHIFT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void deleteShiftWithAuthenticatedWaiterShouldThrowBadRequestException() throws Exception {
        LoginUtil.login(mockMvc, Role.BARTENDER);

        mockMvc.perform(delete(BASE_URL + "/" + EXISTING_SHIFT_ID))
                .andExpect(status().isForbidden());
    }
}
