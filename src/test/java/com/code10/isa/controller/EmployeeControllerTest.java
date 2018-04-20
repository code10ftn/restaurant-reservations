package com.code10.isa.controller;

import com.code10.isa.model.user.Employee;
import com.code10.isa.model.user.Role;
import com.code10.isa.repository.UserRepository;
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

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String BASE_URL = "/api/employees";

    private static final long ADMIN_ID = 1;

    private static final long EMPLOYEE_ID = 3;

    private static final long NON_EXISTENT_ID = 100;

    @Test
    @WithMockUser
    public void updateShouldReturnUpdatedEmployee() throws Exception {
        LoginUtil.login(mockMvc, Role.BARTENDER);
        final Employee employee = (Employee) userRepository.findById(EMPLOYEE_ID).get();
        employee.setShoeSize(10);

        mockMvc.perform(put(BASE_URL + "/" + EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(employee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.shoeSize", is(employee.getShoeSize())));
    }

    @Test
    @WithMockUser
    public void updateWithAdminIdShouldThrowBadRequestException() throws Exception {
        LoginUtil.login(mockMvc, Role.BARTENDER);
        final Employee employee = (Employee) userRepository.findById(EMPLOYEE_ID).get();
        employee.setId(ADMIN_ID);
        employee.setShoeSize(10);

        mockMvc.perform(put(BASE_URL + "/" + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void updateWithNonExistentIdShouldThrowNotFoundException() throws Exception {
        LoginUtil.login(mockMvc, Role.BARTENDER);
        final Employee employee = (Employee) userRepository.findById(EMPLOYEE_ID).get();
        employee.setId(NON_EXISTENT_ID);

        mockMvc.perform(put(BASE_URL + "/" + NON_EXISTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(employee)))
                .andExpect(status().isNotFound());
    }
}
