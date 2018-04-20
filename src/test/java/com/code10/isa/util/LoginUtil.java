package com.code10.isa.util;

import com.code10.isa.model.dto.LoginDto;
import com.code10.isa.model.user.Role;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginUtil {

    public static void login(MockMvc mockMvc, Role role) throws Exception {
        final LoginDto loginDto = new LoginDto("admin@admin", "admin");

        switch (role) {
            case ADMIN:
                loginDto.setEmail("admin@admin");
                loginDto.setPassword("admin");
                break;
            case MANAGER:
                loginDto.setEmail("manager@manager");
                loginDto.setPassword("manager");
                break;
            case WAITER:
                loginDto.setEmail("alek@nik");
                loginDto.setPassword("alek");
                break;
            case BARTENDER:
                loginDto.setEmail("aco@nik");
                loginDto.setPassword("aco");
                break;
            case CHEF:
                break;
            case SUPPLIER:
                break;
            case GUEST:
                loginDto.setEmail("gost@gost");
                loginDto.setPassword("gost");
                break;
        }
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.json(loginDto)))
                .andExpect(status().isOk());
    }
}
