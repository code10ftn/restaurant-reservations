package com.code10.isa.service;

import com.code10.isa.model.dto.AdminRegisterDto;
import com.code10.isa.model.user.Admin;
import com.code10.isa.model.user.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void createShouldReturnSavedAdmin() {
        final AdminRegisterDto testEntity = new AdminRegisterDto("testAdmin@admin", "admin", "admin");

        final Admin createdTestEntity = adminService.create(testEntity);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getRole()).isEqualTo(Role.ADMIN);
        assertThat(createdTestEntity.getEmail()).isEqualTo(testEntity.getEmail());
        assertThat(createdTestEntity.getPassword()).isEqualTo(testEntity.getPassword());
    }
}
