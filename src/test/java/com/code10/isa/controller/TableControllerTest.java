package com.code10.isa.controller;

import com.code10.isa.model.Area;
import com.code10.isa.model.Table;
import com.code10.isa.model.user.Role;
import com.code10.isa.service.EmployeeService;
import com.code10.isa.service.RestaurantService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RestaurantService restaurantService;

    private static final String BASE_URL = "/api/tables";

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Test
    @WithMockUser
    public void getAllTablesShouldReturnOk() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        mockMvc.perform(get(BASE_URL + "/restaurant/" + EXISTING_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void addShiftShouldReturnCreated() throws Exception {
        LoginUtil.login(mockMvc, Role.MANAGER);

        final Table table = new Table(Area.INSIDE_NONSMOKING, 1, 1, 2);
        table.setRestaurant(restaurantService.findById(EXISTING_RESTAURANT_ID));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(table)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }


}
