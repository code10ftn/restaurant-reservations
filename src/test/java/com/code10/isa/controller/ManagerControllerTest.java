package com.code10.isa.controller;

import com.code10.isa.model.dto.ManagerRegisterDto;
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
public class ManagerControllerTest {

    private static final String BASE_URL = "/api/managers";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void signupWithValidDataShouldReturnCreated() throws Exception {

        LoginUtil.login(mockMvc, Role.ADMIN);

        final ManagerRegisterDto managerRegisterDto =
                new ManagerRegisterDto("managerTest@manager", "testPassword", "testPassword", "testFirstName", "testLastName", 1);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(managerRegisterDto)))
                .andExpect(status().isCreated());
    }
}
