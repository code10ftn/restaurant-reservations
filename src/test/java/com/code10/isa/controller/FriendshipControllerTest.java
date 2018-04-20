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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class FriendshipControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/friendships";

    private static final long EXISTING_GUEST_ID_FRIEND = 6;
    private static final long EXISTING_GUEST_ID_RECEIVER = 7;

    @Test
    @WithMockUser
    public void addFriendshipShouldReturnCreated() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);
        final Guest receiver = (Guest) userRepository.findById(EXISTING_GUEST_ID_RECEIVER).get();

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(receiver)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @WithMockUser
    public void addExistingFriendshipShouldReturnBadRequest() throws Exception {
        LoginUtil.login(mockMvc, Role.GUEST);
        final Guest receiver = (Guest) userRepository.findById(EXISTING_GUEST_ID_FRIEND).get();

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(receiver)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
