package com.code10.isa.controller;

import com.code10.isa.model.dto.GuestRegisterDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class AuthControllerTest {

    private static final String BASE_URL = "/api/auth";
    private static final String SIGNUP = "/signup";
    private static final String SIGNIN = "/signin";
    private static final String SIGNOUT = "/signout";
    private static final String AUTHENTICATE = "/authenticate";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signupWithUnavailableEmailShouldReturnBadRequest() throws Exception {
        final GuestRegisterDto guestRegisterDto =
                new GuestRegisterDto("admin@admin", "testPassword", "testPassword", "testFirstName", "testLastName");

        mockMvc.perform(post(BASE_URL + SIGNUP)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(guestRegisterDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signupWithFieldsNotFilledShouldReturnBadRequest() throws Exception {
        final GuestRegisterDto guestRegisterDto =
                new GuestRegisterDto("guest@guest", "testPassword", "testPassword", "testFirstName", "");

        mockMvc.perform(post(BASE_URL + SIGNUP)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(guestRegisterDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signupWithUnmatchedPasswordsShouldReturnBadRequest() throws Exception {
        final GuestRegisterDto guestRegisterDto =
                new GuestRegisterDto("guest@guest", "testPassword", "testPassword1", "testFirstName", "testLastName");

        mockMvc.perform(post(BASE_URL + SIGNUP)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(guestRegisterDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signupWithValidDataShouldReturnCreated() throws Exception {
        final GuestRegisterDto guestRegisterDto =
                new GuestRegisterDto("guest@guest", "testPassword", "testPassword", "testFirstName", "testLastName");

        mockMvc.perform(post(BASE_URL + SIGNUP)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(guestRegisterDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void signinWithInvalidCredentialsShouldReturnNotFound() throws Exception {
        final LoginDto loginDto = new LoginDto("test@test", "testPassword");

        mockMvc.perform(post(BASE_URL + SIGNIN)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(loginDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void signintWithValidCredentialsShouldReturnOk() throws Exception {
        final LoginDto loginDto = new LoginDto("admin@admin", "admin");

        mockMvc.perform(post(BASE_URL + SIGNIN)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.json(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void signoutWhenUnauthenticatedShouldReturnForbidden() throws Exception {
        mockMvc.perform(post(BASE_URL + SIGNOUT))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void signoutWhenAuthenticatedShouldReturnOk() throws Exception {
        mockMvc.perform(post(BASE_URL + SIGNOUT))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticateWhenUnauthenticatedShouldReturnNotAllowed() throws Exception {
        mockMvc.perform(post(BASE_URL + AUTHENTICATE))
                .andExpect(status().isMethodNotAllowed());
    }
}
