package com.code10.isa.controller;

import com.code10.isa.model.user.Guest;
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
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String BASE_URL = "/api/guests";

    private static final long ADMIN_ID = 1;

    private static final long GUEST_ID = 4;

    private static final long NON_EXISTENT_ID = 100;

    @Test
    @WithMockUser
    public void updateShouldReturnUpdatedEmployee() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);
        final Guest guest = (Guest) userRepository.findById(GUEST_ID).get();
        guest.setFirstName("New guest name");

        mockMvc.perform(put(BASE_URL + "/" + GUEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(guest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.firstName", is(guest.getFirstName())));
    }

    @Test
    @WithMockUser
    public void updateWithAdminIdShouldThrowBadRequestException() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);
        final Guest guest = (Guest) userRepository.findById(GUEST_ID).get();
        guest.setId(ADMIN_ID);

        mockMvc.perform(put(BASE_URL + "/" + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(guest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void updateWithNonExistentIdShouldThroNotFoundException() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);
        final Guest guest = (Guest) userRepository.findById(GUEST_ID).get();
        guest.setId(NON_EXISTENT_ID);

        mockMvc.perform(put(BASE_URL + "/" + NON_EXISTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(guest)))
                .andExpect(status().isNotFound());
    }
}
