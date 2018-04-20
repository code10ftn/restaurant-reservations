package com.code10.isa.controller;

import com.code10.isa.model.dto.ChangePasswordDto;
import com.code10.isa.model.dto.LoginDto;
import com.code10.isa.util.JsonUtil;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/users";

    private static final long EXISTING_USER_ID = 3;

    private static final long NON_EXISTENT_ID = 100;

    private static final long UNVERIFIED_USER_ID = 2;

    @Test
    public void findWithExistingIdShouldReturnUser() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", EXISTING_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void findWithNonExistentIdShouldReturnUser() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void updatePasswordWithCorrectDataShouldReturnOk() throws Exception {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto("alek", "a", "a");
        mockMvc.perform(put(BASE_URL + "/{id}", NON_EXISTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(changePasswordDto)))
                .andExpect(status().isNotFound());


        LoginDto loginDto = new LoginDto("alek@nik", "alek");
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(loginDto)))
                .andExpect(status().isOk());

        changePasswordDto = new ChangePasswordDto("alek", "a", "a");
        mockMvc.perform(put(BASE_URL + "/{id}", UNVERIFIED_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(changePasswordDto)))
                .andExpect(status().isOk());

        loginDto = new LoginDto("admin@admin", "admin");
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(loginDto)))
                .andExpect(status().isOk());

        changePasswordDto = new ChangePasswordDto("admin", "new", "new");
        mockMvc.perform(put(BASE_URL + "/{id}", UNVERIFIED_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(changePasswordDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        changePasswordDto = new ChangePasswordDto("adminWrong", "new", "new");
        mockMvc.perform(put(BASE_URL + "/{id}", UNVERIFIED_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(changePasswordDto)))
                .andExpect(status().isBadRequest());

        changePasswordDto = new ChangePasswordDto("admin", "new", "old");
        mockMvc.perform(put(BASE_URL + "/{id}", UNVERIFIED_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(changePasswordDto)))
                .andExpect(status().isBadRequest());

    }
}
