package com.code10.isa.controller;


import com.code10.isa.model.dto.AdminRegisterDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class AdminControllerTest {

    private static final String BASE_URL = "/api/admins";

    private static final String EXISTING_ADMIN = "admin@admin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void signupWithValidDataShouldReturnCreated() throws Exception {

        LoginUtil.login(mockMvc, Role.ADMIN);

        final AdminRegisterDto adminRegisterDto =
                new AdminRegisterDto("admin@test", "test", "test");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(adminRegisterDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void signupWithExistingDataShouldReturnBadRequest() throws Exception {

        LoginUtil.login(mockMvc, Role.ADMIN);

        final AdminRegisterDto adminRegisterDto =
                new AdminRegisterDto(EXISTING_ADMIN, "test", "test");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(adminRegisterDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void signupWithWrongRoleShouldReturnForbidden() throws Exception {

        LoginUtil.login(mockMvc, Role.MANAGER);

        final AdminRegisterDto adminRegisterDto =
                new AdminRegisterDto("admin@test", "test", "test");

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(adminRegisterDto)))
                .andExpect(status().isForbidden());
    }
}
